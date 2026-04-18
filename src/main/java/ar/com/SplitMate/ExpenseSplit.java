
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
    
    
    @ManyToOne()
    @JoinColumn(name= "user_id", nullable= false)
    private User user;
    
    @ManyToOne()
    @JoinColumn(name= "expense_id", nullable = false)
    private Expense expense;
    
    @Column(name = "amount")
    private double amount;
    
    public ExpenseSplit(){};
}
