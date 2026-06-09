package ar.com.splitmate.controllers;

import ar.com.splitmate.Expense;
import ar.com.splitmate.Group;
import ar.com.splitmate.GroupMember;
import ar.com.splitmate.User;
import ar.com.splitmate.forms.ExpenseForm;
import ar.com.splitmate.servicios.ExpenseService;
import ar.com.splitmate.servicios.ExpenseSplitService;
import ar.com.splitmate.servicios.GroupMemberService;
import ar.com.splitmate.servicios.GroupService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final GroupService groupService;
    private final GroupMemberService memberService;
    private final ExpenseSplitService splitService;

    public ExpenseController(ExpenseService expenseService,
                             GroupService groupService,
                             GroupMemberService memberService,
                             ExpenseSplitService splitService) {
        this.expenseService = expenseService;
        this.groupService = groupService;
        this.memberService = memberService;
        this.splitService = splitService;
    }


    @GetMapping("/new")
    public String form(@RequestParam Long groupId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("usuarioLogueado");
        if (user == null) return "redirect:/login";


        GroupMember miembro = memberService.obtenerMiembro(user.getId(), groupId);
        if (miembro == null) return "redirect:/dashboard";

        Group group = groupService.buscarPorId(groupId);
        model.addAttribute("expenseForm", new ExpenseForm());
        model.addAttribute("grupo", group);
        return "expenses/create";
    }

    @PostMapping
    public String create(@ModelAttribute ExpenseForm form,
                         @RequestParam Long groupId,
                         HttpSession session,
                         Model model) {
        User user = (User) session.getAttribute("usuarioLogueado");
        if (user == null) return "redirect:/login";

        try {
            Group group = groupService.buscarPorId(groupId);
            GroupMember miembro = memberService.obtenerMiembro(user.getId(), groupId);

            if (miembro == null) {
                throw new RuntimeException("No sos miembro de este grupo");
            }

            Expense expense = new Expense(form.getDescription(), form.getAmount(), miembro, group);
            expenseService.guardarGasto(expense);

            splitService.createSplit(expense, group.getMembers());
            return "redirect:/groups/" + groupId;

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("expenseForm", form);
            model.addAttribute("grupo", groupService.buscarPorId(groupId));
            return "expenses/create";
        }
    }
}
