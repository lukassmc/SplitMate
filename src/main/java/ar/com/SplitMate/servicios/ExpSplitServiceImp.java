package ar.com.splitmate.servicios;

import ar.com.splitmate.Expense;
import ar.com.splitmate.ExpenseSplit;
import ar.com.splitmate.GroupMember;

import ar.com.splitmate.User;
import ar.com.splitmate.repositorios.ExpenseSplitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpSplitServiceImp implements ExpenseSplitService {

    @Autowired
    private ExpenseSplitRepository expenseSplitRepository;

    public void ExpenseSplitService(ExpenseSplitRepository repository) {
        this.expenseSplitRepository = repository;
    }

    @Override
    public void dividirGasto(Expense expense, List<GroupMember> miembros) {

        double montoPorPersona = expense.getAmount() / miembros.size();

        for (GroupMember miembro : miembros) {

            ExpenseSplit split = new ExpenseSplit(miembro, expense, montoPorPersona);

            expense.addSplit(split);
            expenseSplitRepository.save(split);
        }
    }

    @Override
    public Double calcularBalance(GroupMember member, List<Expense> expenses) {

        double debe = 0;
        double pago = 0;

        for (Expense expense : expenses) {


            if (expense.getPaidBy().equals(member)) {
                pago += expense.getAmount();
            }


            for (ExpenseSplit split : expense.getSplits()) {

                if (split.getMember().equals(member)) {
                    debe += split.getAmount();
                }
            }
        }

        return pago - debe;
    }

    @Override
    public void marcarPagado(Long splitId) {

        ExpenseSplit split = expenseSplitRepository.findById(splitId)
                .orElseThrow();

        split.setPaid(true);

        expenseSplitRepository.save(split);
    }
}

