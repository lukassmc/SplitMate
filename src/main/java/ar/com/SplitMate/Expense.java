
package ar.com.splitmate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity 
@Table( name = "expense")
public class Expense extends Persistible{
    
    @Column(name ="description")
    private String description;
   
    @Column(name ="amount")
    private Double amount;
    
    @ManyToOne
    @JoinColumn(name= "paid_by", nullable = false)
    private GroupMember paidBy;
    
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
    
    
    @OneToMany(mappedBy= "expense")
    private List<ExpenseSplit> splits;

    public Expense( String description, Double amount, GroupMember paidBy, Group group) {
        if (amount == null || amount < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo");
        }
        this.description = description;
        this.amount = amount;
        this.paidBy = paidBy;
        this.group = group;
        this.splits = new ArrayList<>();
    }
    
    public Expense() {};
            
    public void splitExpense(){}

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public GroupMember getPaidBy() {
        return paidBy;
    }

    public Group getGroup() {
        return group;
    }
}
