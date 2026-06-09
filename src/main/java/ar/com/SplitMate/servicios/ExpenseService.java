
package ar.com.splitmate.servicios;

import ar.com.splitmate.Expense;

import java.util.List;

public interface ExpenseService {
    void guardarGasto(Expense expense);
    Expense buscarPorId(Long id);
    List<Expense> listAll();  // NUEVO: para el panel de admin
}

    