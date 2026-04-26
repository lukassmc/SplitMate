package ar.com.splitmate.controllers;

import ar.com.splitmate.Expense;
import ar.com.splitmate.Group;
import ar.com.splitmate.GroupMember;
import ar.com.splitmate.User;
import ar.com.splitmate.forms.ExpenseForm;
import ar.com.splitmate.servicios.ExpenseService;
import ar.com.splitmate.servicios.GroupMemberService;
import ar.com.splitmate.servicios.GroupService;
import ar.com.splitmate.servicios.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;
    private final GroupService groupService;
    private final GroupMemberService memberService;

    public ExpenseController(
            ExpenseService expenseService,
            UserService userService,
            GroupService groupService,
            GroupMemberService memberService
    ) {
        this.expenseService = expenseService;
        this.userService = userService;
        this.groupService = groupService;
        this.memberService = memberService;
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("expenseForm", new ExpenseForm());
        return "expenses/create";
    }

    @PostMapping
    public String create(@ModelAttribute ExpenseForm form, Model model) {

        try {
            User user = userService.buscarPorId(form.getUserId());
            Group group = groupService.buscarPorId(form.getGroupId());

            GroupMember miembro =
                    memberService.obtenerMiembro(user.getId(), group.getId());

            if (miembro == null) {
                throw new RuntimeException("El usuario no pertenece al grupo");
            }

            Expense expense = new Expense(
                    form.getDescription(),
                    form.getAmount(),
                    miembro,
                    group
            );

            expenseService.guardarGasto(expense);

            return "redirect:/";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "expenses/create";
        }
    }
}