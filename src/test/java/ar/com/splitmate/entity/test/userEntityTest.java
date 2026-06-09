
package ar.com.splitmate.entity.test;
import ar.com.splitmate.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;


public class userEntityTest {

    @Test
    void deberiaCrearUsuarioCorrectamente() {
        User user = new User("lucas", "1234");

        assertEquals("lucas", user.getUsername());
        assertEquals("1234", user.getPassword());
    }

    @Test
    void usernameNoDeberiaSerNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User(null, "1234");
        });
    }
}