
package ar.com.splitmate.servicios;

import ar.com.splitmate.Expense;
import ar.com.splitmate.ExpenseSplit;
import ar.com.splitmate.GroupMember;
import ar.com.splitmate.repositorios.ExpenseRepository;
import ar.com.splitmate.repositorios.ExpenseSplitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImp implements ExpenseService {
    
    @Autowired
    private ExpenseRepository repository;
    @Autowired
    private ExpenseSplitRepository expSplitRepository;
    
    @Override
    public Expense guardarGasto(Expense expense){
        this.repository.save(expense);
        return expense;
    }

    public void dividirGasto(Expense expense) {

        List<GroupMember> miembros = expense.getGroup().getMembers();

        double montoPorPersona = expense.getAmount() / miembros.size();

        for (GroupMember miembro : miembros) {

            ExpenseSplit split = new ExpenseSplit();
            split.setExpense(expense);
            split.setMember(miembro);
            split.setAmount(montoPorPersona);

            expSplitRepository.save(split);
        }
    }

    @Override
    public List<Expense> listAll(){ return this.repository.findAll();};
}