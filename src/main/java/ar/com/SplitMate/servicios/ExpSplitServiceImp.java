package ar.com.splitmate.servicios;

import ar.com.splitmate.Expense;
import ar.com.splitmate.ExpenseSplit;
import ar.com.splitmate.GroupMember;

import ar.com.splitmate.dto.BalanceDTO;
import ar.com.splitmate.repositorios.ExpenseSplitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpSplitServiceImp implements ExpenseSplitService {

    @Autowired
    private ExpenseSplitRepository expenseSplitRepository;

    public void ExpenseSplitService(ExpenseSplitRepository repository) {
        this.expenseSplitRepository = repository;
    }

    @Override
    public void createSplit(Expense expense, List<GroupMember> participantes) {
        if (expense == null || participantes == null || participantes.isEmpty()) {
            throw new IllegalArgumentException("Argumentos incorrectos para crear split.");
        }

        double splitAmount = expense.getAmount() / participantes.size();

        for (GroupMember miembro : participantes) {
            ExpenseSplit split = new ExpenseSplit();
            split.setExpense(expense);
            split.setAmount(splitAmount);
            split.setMember(miembro);
            expenseSplitRepository.save(split);
        }
    }

    @Override
    public Map<String, BalanceDTO> calcularBalances(Long groupId) {
        List<ExpenseSplit> splits = expenseSplitRepository.findByGroupId(groupId);

        // username → { totalPagado, totalDebido }
        Map<String, double[]> acumulado = new HashMap<>();

        for (ExpenseSplit split : splits) {
            String deudor   = split.getMember().getUser().getUsername();
            String pagador  = split.getExpense().getPaidBy().getUser().getUsername();
            double monto    = split.getAmount();

            // El deudor debe este monto (lo suma a su totalOwed)
            acumulado.computeIfAbsent(deudor, k -> new double[]{0, 0})[1] += monto;

            // El pagador adelantó este monto (lo suma a su totalPaid)
            acumulado.computeIfAbsent(pagador, k -> new double[]{0, 0})[0] += monto;
        }

        Map<String, BalanceDTO> balances = new HashMap<>();
        for (Map.Entry<String, double[]> entry : acumulado.entrySet()) {
            String username  = entry.getKey();
            double totalPaid = entry.getValue()[0];
            double totalOwed = entry.getValue()[1];
            balances.put(username, new BalanceDTO(username, totalPaid, totalOwed));
        }

        return balances;
    }

    @Override
    public void markAsPaid(Long splitId) {
        ExpenseSplit split = expenseSplitRepository.findById(splitId)
                .orElseThrow(() -> new RuntimeException("Split no encontrado: " + splitId));
        split.markAsPaid();
        expenseSplitRepository.save(split);
    }

    @Override
    public List<ExpenseSplit> obtenerPendientes(Long groupId, Long memberId) {
        return expenseSplitRepository.findPendingByGroupAndMember(groupId, memberId);
    }
}

