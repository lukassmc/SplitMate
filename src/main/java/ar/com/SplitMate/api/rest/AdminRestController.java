package ar.com.splitmate.api.rest;


import ar.com.splitmate.Expense;
import ar.com.splitmate.Group;
import ar.com.splitmate.User;
import ar.com.splitmate.dto.responseDTO.AdminDashboardResponse;
import ar.com.splitmate.servicios.ExpenseService;
import ar.com.splitmate.servicios.GroupService;
import ar.com.splitmate.servicios.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final UserService userService;
    private final GroupService groupService;
    private final ExpenseService expenseService;


    public AdminRestController(UserService userService, GroupService groupService, ExpenseService expenseService) {
        this.userService = userService;
        this.groupService = groupService;
        this.expenseService = expenseService;
    }


    @PostMapping("/users/{id}/promote")
    public void promoteUser(@PathVariable Long id){

        User user = userService.buscarPorId(id);
        user.turnToAdmin();

        userService.guardarUsuario(user);

    }

    @GetMapping("/dashboard/getData")
    public AdminDashboardResponse dashboard(){
        List<User> usuarios= userService.listAll();
        List<Group> grupos= groupService.listAll();
        List<Expense> gastos= expenseService.listAll();
        Integer totalUsuarios = usuarios.size();
        Integer totalGrupos = grupos.size();
        Integer totalGastos = gastos.size();




        return new AdminDashboardResponse(usuarios, grupos,gastos, totalUsuarios,totalGrupos, totalGastos);
    };


}
