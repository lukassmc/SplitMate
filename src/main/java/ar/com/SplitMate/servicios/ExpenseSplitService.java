package ar.com.splitmate.servicios;


import ar.com.splitmate.Expense;
import ar.com.splitmate.ExpenseSplit;

import ar.com.splitmate.GroupMember;
import ar.com.splitmate.User;

import java.util.List;

public interface ExpenseSplitService {

    public void dividirGasto(Expense expense, List<GroupMember> participantes);

    public void marcarPagado(Long splitId);

    public Double calcularBalance(GroupMember member, List<Expense> expenses);
}
