
package ar.com.splitmate.servicios;

import ar.com.splitmate.Expense;
import ar.com.splitmate.repositorios.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseServiceImp implements ExpenseService {
    
    @Autowired
    private ExpenseRepository repository;
    
    @Override
    public void guardarGasto(Expense expense){
        this.repository.save(expense);
    }
}