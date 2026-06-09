
package ar.com.splitmate;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinColumn(name= "group_member_id", nullable= false)
    private GroupMember member;
    
    @ManyToOne()
    @JoinColumn(name= "expense_id", nullable = false)
    @JsonIgnore
    private Expense expense;
    
    @Column(name = "amount")
    private double amount;

    @Column(name = "is_paid", nullable = false)
    private boolean is_paid;


    
    public ExpenseSplit(){};

    public ExpenseSplit( GroupMember member, Expense expense, double amount) {
        if (!(member == null) && !(expense == null) && amount < 1 )
        { throw new RuntimeException("Ingrese los valores correctamente.");
        };
        this.member = member;
        this.expense = expense;
        this.amount = amount;
        this.is_paid = false;
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

    public boolean isPaid() {
        return is_paid;
    }

    public void markAsPaid()    { this.is_paid = true; }


    public void setPaid(boolean paid) {
        this.is_paid = paid;
    }
}


// En caso de que, se registre un gasto de 100 total entre 5, pero uno puso 18, otro 22,y asi etc, como se contemp´laria//