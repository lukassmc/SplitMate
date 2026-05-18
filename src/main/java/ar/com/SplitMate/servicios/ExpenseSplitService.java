package ar.com.splitmate.servicios;


import ar.com.splitmate.Expense;
import ar.com.splitmate.ExpenseSplit;

import ar.com.splitmate.GroupMember;

import java.util.List;

public interface ExpenseSplitService {

    public void createSplit(Expense expense, List<GroupMember> participantes);
}
