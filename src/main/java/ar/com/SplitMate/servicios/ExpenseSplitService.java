package ar.com.splitmate.servicios;


import ar.com.splitmate.Expense;
import ar.com.splitmate.ExpenseSplit;

import ar.com.splitmate.GroupMember;
import ar.com.splitmate.dto.BalanceDTO;

import java.util.List;
import java.util.Map;

public interface ExpenseSplitService {


    void createSplit(Expense expense, List<GroupMember> participantes);


    // clave: username, valor: BalanceDTO con totales
    Map<String, BalanceDTO> calcularBalances(Long groupId);


    void markAsPaid(Long splitId);


    List<ExpenseSplit> obtenerPendientes(Long groupId, Long memberId);
}

