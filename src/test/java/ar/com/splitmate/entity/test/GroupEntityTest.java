
package ar.com.splitmate.entity.test;
import ar.com.splitmate.Group;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GroupEntityTest {

    @Test
    void deberiaCrearGrupo() {
        Group group = new Group("Viaje");

        assertEquals("Viaje", group.getName());
    }

    @Test
    void nombreNoDeberiaSerVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Group("");
        });
    }
}