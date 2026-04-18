
package ar.com.splitmate.integration.test;
import ar.com.splitmate.*;
import ar.com.splitmate.servicios.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SplitmateIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupMemberService memberService;

    @Autowired
    private ExpenseService expenseService;

    @Test
    void flujoCompleto_deberiaFuncionar() {

        
        User user = new User("testUser", "1234");
        userService.guardarUsuario(user);

        assertNotNull(user.getId());

        Group group = new Group("testGroup");
        groupService.guardarGrupo(group);

        assertNotNull(group.getId());


        GroupMember member = new GroupMember(user, group);
        groupService.agregarMiembro(member);

        GroupMember encontrado = memberService.obtenerMiembro(user.getId(), group.getId());
        assertNotNull(encontrado);

      
        Expense expense = new Expense("Cena", 1000, encontrado, group);
        expenseService.guardarGasto(expense);

        assertNotNull(expense.getId());
    }
}