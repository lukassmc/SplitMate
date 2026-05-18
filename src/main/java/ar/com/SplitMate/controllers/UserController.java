package ar.com.splitmate.controllers;

import ar.com.splitmate.User;
import ar.com.splitmate.forms.LoginForm;
import ar.com.splitmate.forms.UserForm;
import ar.com.splitmate.servicios.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ── REGISTER ────────────────────────────────────────────────────────────

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserForm form, Model model) {

        if (form.getUsername() == null || form.getUsername().length() < 3) {
            model.addAttribute("error", "El username debe tener al menos 3 caracteres");
            model.addAttribute("userForm", form);
            return "register";
        }
        if (form.getPassword() == null || form.getPassword().length() < 3) {
            model.addAttribute("error", "La contraseña debe tener al menos 3 caracteres");
            model.addAttribute("userForm", form);
            return "register";
        }

        // Verificar que no exista el username
        if (userService.buscarPorUsername(form.getUsername()) != null) {
            model.addAttribute("error", "Ese username ya está en uso");
            model.addAttribute("userForm", form);
            return "register";
        }

        User user = new User(form.getUsername(), form.getPassword());
        userService.guardarUsuario(user);
        return "redirect:/login?registered=true";
    }

    // ── LOGIN ────────────────────────────────────────────────────────────────

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginForm form, HttpSession session, Model model) {
        User user = userService.buscarPorUsername(form.getUsername());

        if (user == null || !user.login(form.getUsername(), form.getPassword())) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            model.addAttribute("loginForm", form);
            return "login";
        }

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.collectAuthorities()
                );

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context
        );

        session.setAttribute("usuarioLogueado", user);
        System.out.println(authentication.isAuthenticated());
        System.out.println(authentication.getAuthorities());
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/dashboard";
    }


}
