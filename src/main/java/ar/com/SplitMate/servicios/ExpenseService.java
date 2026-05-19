
package ar.com.splitmate.servicios;

import ar.com.splitmate.Expense;
import ar.com.splitmate.User;

import java.util.List;

public interface ExpenseService {
    Expense guardarGasto(Expense expense);

    List<Expense> listAll();

}
    