package ar.com.splitmate.dto.responseDTO;

import ar.com.splitmate.Expense;
import ar.com.splitmate.Group;
import ar.com.splitmate.User;

import java.util.List;

public record AdminDashboardResponse(
        List<User> usuarios,
        List<Group> grupos,
        List<Expense> gastos,
        Integer totalUsuarios,
        Integer totalGrupos,
        Integer totalGastos
) {
}
