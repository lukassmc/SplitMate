
package ar.com.SplitMate;

import java.util.ArrayList;
import java.util.List;


public class Expense {
    private long id;
    private String description;
    private double amount;
    private User paidBy;
    private Group group;
    private List<ExpenseSplit> splits;

    public Expense(long id, String description, double amount, User paidBy, Group group) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.paidBy = paidBy;
        this.group = group;
        this.splits = new ArrayList<>();
    }
    
    
    public void splitExpense(){}

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public Group getGroup() {
        return group;
    }
}
