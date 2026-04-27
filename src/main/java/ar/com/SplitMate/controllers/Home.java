package ar.com.splitmate.controllers;

import ar.com.splitmate.User;
import ar.com.splitmate.servicios.GroupService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Home {
    private final GroupService groupService;

    public Home(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/")
    public String root(HttpSession session) {
        // Si hay sesión activa, va al dashboard. Si no, al login.
        User user = (User) session.getAttribute("usuarioLogueado");
        return user != null ? "redirect:/dashboard" : "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("usuarioLogueado");
        if (user == null) return "redirect:/login";

        // Carga los grupos en los que el usuario participa
        model.addAttribute("usuario", user);
        model.addAttribute("grupos", groupService.buscarGruposDeUsuario(user.getId()));
        return "dashboard";
    }
}
