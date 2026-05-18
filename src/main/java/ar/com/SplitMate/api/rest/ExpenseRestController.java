package ar.com.splitmate.api.rest;

import ar.com.splitmate.Expense;
import ar.com.splitmate.Group;
import ar.com.splitmate.GroupMember;
import ar.com.splitmate.dto.ExpenseRequest;
import ar.com.splitmate.servicios.ExpenseService;
import ar.com.splitmate.servicios.GroupMemberService;
import ar.com.splitmate.servicios.GroupService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseRestController {

    private final ExpenseService expenseService;
    private final GroupService groupService;
    private final GroupMemberService memberService;

    public ExpenseRestController(ExpenseService expenseService,
                                 GroupService groupService,
                                 GroupMemberService memberService) {
        this.expenseService = expenseService;
        this.groupService = groupService;
        this.memberService = memberService;
    }

    @PostMapping
    public Expense create(@RequestBody ExpenseRequest req) {

        Group group = groupService.buscarPorId(req.groupId());
        GroupMember miembro = memberService.obtenerMiembro(req.userId(), req.groupId());

        if (miembro == null) {
            throw new RuntimeException("No pertenece al grupo");
        }

        Expense expense = new Expense(
                req.description(),
                req.amount(),
                miembro,
                group
        );

        return expenseService.guardarGasto(expense);
    }
}
