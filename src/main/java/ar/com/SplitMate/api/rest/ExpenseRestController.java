package ar.com.splitmate.api.rest;

import ar.com.splitmate.Expense;
import ar.com.splitmate.Group;
import ar.com.splitmate.GroupMember;
import ar.com.splitmate.dto.ExpenseRequest;
import ar.com.splitmate.servicios.ExpenseService;
import ar.com.splitmate.servicios.GroupMemberService;
import ar.com.splitmate.servicios.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("list")
    public ResponseEntity<List<Expense>> list(){
        List<Expense> expenses = this.expenseService.listAll();

        return ResponseEntity.ok(expenses);

    }

    @PostMapping
    public void create(@RequestBody ExpenseRequest req) {

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

    }


}
