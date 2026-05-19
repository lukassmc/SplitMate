package ar.com.splitmate.controllers;

import ar.com.splitmate.User;
import ar.com.splitmate.servicios.ExpenseService;
import ar.com.splitmate.servicios.GroupService;
import ar.com.splitmate.servicios.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final GroupService groupService;
    private final ExpenseService expenseService;

    public AdminController(UserService userService,
                           GroupService groupService,
                           ExpenseService expenseService) {
        this.userService = userService;
        this.groupService = groupService;
        this.expenseService = expenseService;
    }

    // Verifica que el usuario logueado sea ADMINISTRADOR
    private boolean esAdmin(HttpSession session) {
        User user = (User) session.getAttribute("usuarioLogueado");
        if (user == null) return false;
        return user.getPermisos() != null &&
                user.getPermisos().stream().anyMatch(p -> p.name().equals("ADMINISTRADOR"));
    }

    @GetMapping("")
    public String dashboard(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/dashboard";

        model.addAttribute("usuarios", userService.listAll());
        model.addAttribute("grupos",   groupService.listAll());
        model.addAttribute("gastos",   expenseService.listAll());
        model.addAttribute("totalUsuarios", userService.listAll().size());
        model.addAttribute("totalGrupos",   groupService.listAll().size());
        model.addAttribute("totalGastos",   expenseService.listAll().size());
        return "admin/dashboard";
    }

    @PostMapping("/usuarios/{id}/promote")
    public String promoteToAdmin(@PathVariable Long id, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/dashboard";
        User user = userService.buscarPorId(id);
        user.turnToAdmin();
        userService.guardarUsuario(user);
        return "redirect:/admin";
    }
}
