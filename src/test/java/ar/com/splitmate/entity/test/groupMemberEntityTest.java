
package ar.com.splitmate.entity.test;

import ar.com.splitmate.*;
import ar.com.splitmate.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class groupMemberEntityTest {

    @Test
    void deberiaCrearRelacionUsuarioGrupo() {
        User user = new User("ana", "123");
        Group group = new Group("Fiesta");

        GroupMember member = new GroupMember(user, group);

        assertEquals(user, member.getUser());
        assertEquals(group, member.getGroup());
    }
}