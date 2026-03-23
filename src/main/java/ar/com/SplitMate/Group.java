
package ar.com.SplitMate;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String name;
    private long id;
    private List<GroupMember>members;
    private List<Expense> expenses;

    public Group(String name, long id) {
        this.name = name;
        this.id = id;
        this.members = new ArrayList<>();
        this.expenses = new ArrayList<>();

    }
    
    
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
    
    
    
}
