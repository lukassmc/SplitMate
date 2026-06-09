package ar.com.splitmate.entity.test;

import ar.com.splitmate.*;
import ar.com.splitmate.enums.Role;
import ar.com.splitmate.enums.Permisos;
import ar.com.splitmate.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// ─────────────────────────────────────────────────────────────────────────────
// TESTS UNITARIOS — no levantan Spring, prueban lógica de dominio pura
// ─────────────────────────────────────────────────────────────────────────────

class UserEntityTest {

    @Test
    void deberiaCrearUsuarioConPermisosBasicos() {
        User user = new User("lucas", "1234");
        assertEquals("lucas", user.getUsername());
        assertEquals("1234", user.getPassword());
        assertTrue(user.getPermisos().contains(Permisos.USUARIO));
    }

    @Test
    void loginCorrectoDeberiaRetornarTrue() {
        User user = new User("lucas", "1234");
        assertTrue(user.login("lucas", "1234"));
    }

    @Test
    void loginIncorrectoDeberiaRetornarFalse() {
        User user = new User("lucas", "1234");
        assertFalse(user.login("lucas", "wrong"));
    }

    @Test
    void turnToAdminDeberiaAgregarPermiso() {
        User user = new User("lucas", "1234");
        assertFalse(user.getPermisos().contains(Permisos.ADMINISTRADOR));
        user.turnToAdmin();
        assertTrue(user.getPermisos().contains(Permisos.ADMINISTRADOR));
    }

    @Test
    void collectAuthoritiesDeberiaRetornarAuthorities() {
        User user = new User("lucas", "1234");
        var authorities = user.collectAuthorities();
        assertFalse(authorities.isEmpty());
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_USUARIO")));
    }
}

class GroupEntityTest {

    @Test
    void deberiaCrearGrupoConCodigo() {
        Group group = new Group("Viaje a Mendoza");
        assertEquals("Viaje a Mendoza", group.getName());
        assertNotNull(group.getInviteCode());
        assertEquals(6, group.getInviteCode().length());
    }

    @Test
    void nombreVacioDeberiaLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Group(""));
        assertThrows(IllegalArgumentException.class, () -> new Group(null));
    }

    @Test
    void codigosDistintosEntreGrupos() {
        Group g1 = new Group("Grupo A");
        Group g2 = new Group("Grupo B");
        assertNotEquals(g1.getInviteCode(), g2.getInviteCode());
    }

    @Test
    void agregarMiembroDeberiaIncrementarLista() {
        Group group = new Group("Grupo Test");
        User user = new User("lucas", "1234");
        GroupMember member = new GroupMember(user, group, Role.ADMIN);
        group.addMember(member);
        assertEquals(1, group.getMembers().size());
    }

    @Test
    void agregarMismoMiembroNoDeberiaduplicar() {
        Group group = new Group("Grupo Test");
        User user = new User("lucas", "1234");
        GroupMember member = new GroupMember(user, group, Role.ADMIN);
        group.addMember(member);
        group.addMember(member); // mismo objeto
        assertEquals(1, group.getMembers().size());
    }
}

class GroupMemberEntityTest {

    @Test
    void deberiaCrearMiembroConRol() {
        User user = new User("lucas", "1234");
        Group group = new Group("Grupo Test");
        GroupMember member = new GroupMember(user, group, Role.ADMIN);

        assertEquals(user, member.getUser());
        assertEquals(group, member.getGroup());
        assertEquals(Role.ADMIN, member.getRole());
    }

    @Test
    void constructorSinRolDeberiaAsignarMember() {
        User user = new User("lucas", "1234");
        Group group = new Group("Grupo Test");
        GroupMember member = new GroupMember(user, group);
        assertEquals(Role.MEMBER, member.getRole());
    }
}

class ExpenseEntityTest {

    @Test
    void deberiaCrearGastoCorrectamente() {
        User user = new User("lucas", "1234");
        Group group = new Group("Grupo Test");
        GroupMember member = new GroupMember(user, group, Role.ADMIN);
        Expense expense = new Expense("Cena", 1000.0, member, group);

        assertEquals("Cena", expense.getDescription());
        assertEquals(1000.0, expense.getAmount());
        assertEquals(member, expense.getPaidBy());
    }

    @Test
    void montoNegativoDeberiaLanzarExcepcion() {
        User user = new User("lucas", "1234");
        Group group = new Group("Grupo Test");
        GroupMember member = new GroupMember(user, group, Role.ADMIN);
        assertThrows(IllegalArgumentException.class,
                () -> new Expense("Cena", -100.0, member, group));
    }

    @Test
    void montoNuloDeberiaLanzarExcepcion() {
        User user = new User("lucas", "1234");
        Group group = new Group("Grupo Test");
        GroupMember member = new GroupMember(user, group, Role.ADMIN);
        assertThrows(IllegalArgumentException.class,
                () -> new Expense("Cena", null, member, group));
    }
}

class ExpenseSplitEntityTest {

    @Test
    void markAsPaidDeberiaMarcarComoPagado() {
        ExpenseSplit split = new ExpenseSplit();
        assertFalse(split.isPaid());
        split.markAsPaid();
        assertTrue(split.isPaid());
    }

    @Test
    void splitDeberiaGuardarMonto() {
        ExpenseSplit split = new ExpenseSplit();
        split.setAmount(500.0);
        assertEquals(500.0, split.getAmount());
    }
}
