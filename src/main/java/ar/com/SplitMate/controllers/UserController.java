package ar.com.splitmate.controllers;

import ar.com.splitmate.User;
import ar.com.splitmate.forms.UserForm;
import ar.com.splitmate.servicios.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "users/create";
    }

    @PostMapping
    public String create(@ModelAttribute UserForm form) {
        User user = new User(form.getUsername(), form.getPassword());
        userService.guardarUsuario(user);
        return "redirect:/";
    }
}