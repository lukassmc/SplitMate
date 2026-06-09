package ar.com.splitmate.integration.test;

import ar.com.splitmate.*;
import ar.com.splitmate.enums.Role;
import ar.com.splitmate.servicios.*;
import ar.com.splitmate.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de integración: levantan el contexto de Spring completo y usan la BD.
 * Requieren MySQL corriendo. Se pueden aislar con un perfil "test" y H2.
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SplitmateIntegrationTest {

    @Autowired private UserService userService;
    @Autowired private GroupService groupService;
    @Autowired private GroupMemberService memberService;
    @Autowired private ExpenseService expenseService;
    @Autowired private ExpenseSplitService splitService;

    // IDs compartidos entre tests (orden garantizado)
    private static Long userId1, userId2, groupId, expenseId;

    @Test
    @Order(1)
    void crearUsuariosDeberiaFuncionar() {
        User u1 = new User("integUser1_" + System.currentTimeMillis(), "pass");
        User u2 = new User("integUser2_" + System.currentTimeMillis(), "pass");
        userService.guardarUsuario(u1);
        userService.guardarUsuario(u2);

        assertNotNull(u1.getId());
        assertNotNull(u2.getId());

        userId1 = u1.getId();
        userId2 = u2.getId();
    }

    @Test
    @Order(2)
    void crearGrupoDeberiaGenerarCodigo() {
        Group group = new Group("IntegGroup_" + System.currentTimeMillis());
        groupService.guardarGrupo(group);

        assertNotNull(group.getId());
        assertNotNull(group.getInviteCode());
        assertEquals(6, group.getInviteCode().length());

        groupId = group.getId();
    }

    @Test
    @Order(3)
    void agregarMiembrosAlGrupo() {
        User u1 = userService.buscarPorId(userId1);
        User u2 = userService.buscarPorId(userId2);
        Group group = groupService.buscarPorId(groupId);

        GroupMember admin  = new GroupMember(u1, group, Role.ADMIN);
        GroupMember member = new GroupMember(u2, group, Role.MEMBER);
        groupService.agregarMiembro(admin);
        groupService.agregarMiembro(member);

        assertNotNull(memberService.obtenerMiembro(userId1, groupId));
        assertNotNull(memberService.obtenerMiembro(userId2, groupId));
    }

    @Test
    @Order(4)
    void crearGastoYSplitDeberiaFuncionar() {
        GroupMember pagador = memberService.obtenerMiembro(userId1, groupId);
        GroupMember deudor  = memberService.obtenerMiembro(userId2, groupId);
        Group group = groupService.buscarPorId(groupId);

        Expense expense = new Expense("Cena integración", 1000.0, pagador, group);
        expenseService.guardarGasto(expense);
        assertNotNull(expense.getId());

        expenseId = expense.getId();

        // Crear splits entre ambos miembros
        splitService.createSplit(expense, List.of(pagador, deudor));
    }

    @Test
    @Order(5)
    void calcularBalancesDeberiaReflejarElGasto() {
        Map<String, ?> balances = splitService.calcularBalances(groupId);
        assertFalse(balances.isEmpty());

        // El que pagó debería tener balance positivo o neutro
        // (dependiendo de si también tiene un split a su nombre)
    }

    @Test
    @Order(6)
    void marcarSplitComoPagadoDeberiaActualizarEstado() {
        GroupMember deudor = memberService.obtenerMiembro(userId2, groupId);
        var pendientes = splitService.obtenerPendientes(groupId, deudor.getId());

        assertFalse(pendientes.isEmpty(), "Debe haber al menos un split pendiente");

        Long splitId = pendientes.get(0).getId();
        splitService.markAsPaid(splitId);

        // Verificar que ya no aparece en pendientes
        var pendientesPost = splitService.obtenerPendientes(groupId, deudor.getId());
        assertTrue(pendientesPost.stream().noneMatch(s -> s.getId().equals(splitId)));
    }

    @Test
    @Order(7)
    void buscarPorCodigoDeberiaEncontrarElGrupo() {
        Group group = groupService.buscarPorId(groupId);
        String code = group.getInviteCode();

        Group encontrado = groupService.buscarPorCodigo(code);
        assertNotNull(encontrado);
        assertEquals(groupId, encontrado.getId());
    }

    @Test
    @Order(8)
    void buscarGruposDeUsuarioDeberiaRetornarSusGrupos() {
        List<Group> grupos = groupService.buscarGruposDeUsuario(userId1);
        assertTrue(grupos.stream().anyMatch(g -> g.getId().equals(groupId)));
    }
}
