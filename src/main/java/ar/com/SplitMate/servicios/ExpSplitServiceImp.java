package ar.com.splitmate.servicios;

import ar.com.splitmate.Expense;
import ar.com.splitmate.ExpenseSplit;
import ar.com.splitmate.Group;
import ar.com.splitmate.GroupMember;

import ar.com.splitmate.repositorios.ExpenseSplitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpSplitServiceImp implements ExpenseSplitService {

    @Autowired
    private ExpenseSplitRepository expenseSplitRepository;

    @Override
    public void createSplit(Expense expense, List<GroupMember> participantes) {

        if (expense == null || participantes.isEmpty()) {
            throw new IllegalArgumentException("Argumentos erroneos.");
        }
        ;

        int splitAmount = (int) (expense.getAmount() / participantes.size());


        for (GroupMember miembro : participantes) {

            ExpenseSplit split = new ExpenseSplit();

            split.setExpense(expense);
            split.setAmount(splitAmount);
            split.setMember(miembro);

            expenseSplitRepository.save(split);
        }
    };
}

