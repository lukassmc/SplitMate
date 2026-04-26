
package ar.com.splitmate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "grupo")
public class Group extends Persistible {
    
    
    @Column(name = "name")
    private String name;
    
    @OneToMany(mappedBy= "group")
    private List<GroupMember>members = new ArrayList<>();
    
    @OneToMany(mappedBy= "group")
    private List<Expense> expenses = new ArrayList<>();

    public Group(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        this.name = name;
        this.members = new ArrayList<>();
        this.expenses = new ArrayList<>();

    }
    
    public Group(){};
    
    public void addMember(GroupMember user){
    
        if(!this.members.contains(user)){
            this.members.add(user);
        }else {
            System.out.println("Este usuario ya se encuentra como miembro del grupo.");
            
        }
        
    }
    
    public void deleteMember(GroupMember user){
        if(this.members.contains(user)){
                this.members.remove(user);
            }else {
                System.out.println("Este usuario no se encuentra como miembro del grupo.");

            }
    }
    
    public void addExpense(Expense expense){
        this.expenses.add(expense);
        
        System.out.println("\nGasto registrado:");
        System.out.println("Grupo: " + this.name);
        System.out.println("Descripción: " + expense.getDescription());
        System.out.println("Monto: $" + expense.getAmount());
    }

    public String getName() {
        return name;
    }

   
    
    
    
}
