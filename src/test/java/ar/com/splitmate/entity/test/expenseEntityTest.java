
package ar.com.splitmate.entity.test;

import ar.com.splitmate.*;
import ar.com.splitmate.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class expenseEntityTest {

    @Test
    void deberiaCrearGasto() {
        User user = new User("lucas", "123");
        Group group = new Group("Asado");
        GroupMember member = new GroupMember(user, group);


        Expense expense = new Expense("Carne", Double.valueOf(5000), member, group);

        assertEquals("Carne", expense.getDescription());
        assertEquals(5000, expense.getAmount());
        assertEquals(member, expense.getPaidBy());
    }

    @Test
    void montoNoPuedeSerNegativo() {
        User user = new User("lucas", "123");
        Group group = new Group("Asado");
        GroupMember member = new GroupMember(user, group);

        assertThrows(IllegalArgumentException.class, () -> {
            new Expense("Carne", Double.valueOf(-100), member, group);
        });
    }
}