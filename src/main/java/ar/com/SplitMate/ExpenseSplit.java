
package ar.com.splitmate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table( name = "expense_split")
public class ExpenseSplit extends Persistible{
    

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne()
    @JoinColumn(name= "user_id", nullable= false)
    private GroupMember member;
    
    @ManyToOne()
    @JoinColumn(name= "expense_id", nullable = false)
    private Expense expense;
    
    @Column(name = "amount")
    private double amount;
    
    public ExpenseSplit(){};

    public ExpenseSplit(Long id, GroupMember member, Expense expense, double amount) {
        if (!(member == null) || !(expense == null) || amount < 1 ){ throw new RuntimeException("Ingrese los valores correctamente.");};

        this.id = id;
        this.amount = amount;
    }

    public void setMember(GroupMember member) {
        this.member = member;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public Long getId() {
        return id;
    }

    public GroupMember getMember() {
        return member;
    }

    public Expense getExpense() {
        return expense;
    }

    public double getAmount() {
        return amount;
    }
}
