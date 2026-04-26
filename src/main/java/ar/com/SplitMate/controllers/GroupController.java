package ar.com.splitmate.controllers;

import ar.com.splitmate.Group;
import ar.com.splitmate.forms.GroupForm;
import ar.com.splitmate.servicios.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("groupForm", new GroupForm());
        return "groups/group-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute GroupForm form) {
        Group group = new Group(form.getName());
        groupService.guardarGrupo(group);
        return "redirect:/";
    }
}