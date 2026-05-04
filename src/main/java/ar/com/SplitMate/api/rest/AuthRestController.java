package ar.com.splitmate.api.rest;

import ar.com.splitmate.User;
import ar.com.splitmate.forms.LoginForm;
import ar.com.splitmate.forms.UserForm;
import ar.com.splitmate.servicios.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final UserService userService;

    public AuthRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody UserForm form) {

        if (userService.buscarPorUsername(form.getUsername()) != null) {
            throw new RuntimeException("Username ya en uso");
        }

        User user = new User(form.getUsername(), form.getPassword());
        return userService.guardarUsuario(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginForm form) {

        User user = userService.buscarPorUsername(form.getUsername());

        if (user == null || !user.login(form.getUsername(), form.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return user;
    }
}
