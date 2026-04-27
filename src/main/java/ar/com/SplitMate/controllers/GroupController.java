package ar.com.splitmate.controllers;

import ar.com.splitmate.Group;
import ar.com.splitmate.GroupMember;
import ar.com.splitmate.Role;
import ar.com.splitmate.User;
import ar.com.splitmate.forms.GroupForm;
import ar.com.splitmate.servicios.GroupMemberService;
import ar.com.splitmate.servicios.GroupService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;
    private final GroupMemberService memberService;

    public GroupController(GroupService groupService, GroupMemberService memberService) {
        this.groupService = groupService;
        this.memberService = memberService;
    }

    // ── CREAR GRUPO ──────────────────────────────────────────────────────────

    @GetMapping("/new")
    public String showForm(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        model.addAttribute("groupForm", new GroupForm());
        return "groups/group-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute GroupForm form, HttpSession session) {
        User user = (User) session.getAttribute("usuarioLogueado");
        if (user == null) return "redirect:/login";

        // Crear el grupo
        Group group = new Group(form.getName());
        groupService.guardarGrupo(group);


        GroupMember admin = new GroupMember(user, group, Role.ADMIN);
        groupService.agregarMiembro(admin);


        return "redirect:/groups/" + group.getId();
    }

    // ── DETALLE DEL GRUPO ────────────────────────────────────────────────────

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("usuarioLogueado");
        if (user == null) return "redirect:/login";

        Group group = groupService.buscarPorId(id);


        GroupMember miembro = memberService.obtenerMiembro(user.getId(), id);
        if (miembro == null) return "redirect:/dashboard";

        model.addAttribute("grupo", group);
        model.addAttribute("miembro", miembro);
        model.addAttribute("usuario", user);
        return "groups/detail";
    }

    // ── UNIRSE POR CÓDIGO ────────────────────────────────────────────────────

    @GetMapping("/join")
    public String showJoin(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        return "groups/join";
    }

    @PostMapping("/join")
    public String join(@RequestParam String inviteCode, HttpSession session, Model model) {
        User user = (User) session.getAttribute("usuarioLogueado");
        if (user == null) return "redirect:/login";

        Group group = groupService.buscarPorCodigo(inviteCode.trim());

        if (group == null) {
            model.addAttribute("error", "Código inválido. Verificá que esté bien escrito.");
            return "groups/join";
        }


        GroupMember existente = memberService.obtenerMiembro(user.getId(), group.getId());
        if (existente != null) {
            model.addAttribute("error", "Ya sos miembro de ese grupo.");
            return "groups/join";
        }

        GroupMember nuevoMiembro = new GroupMember(user, group, Role.MEMBER);
        groupService.agregarMiembro(nuevoMiembro);

        return "redirect:/groups/" + group.getId();
    }
}
