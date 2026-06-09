package ar.com.splitmate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "grupo")
public class Group extends Persistible {

    @Column(name = "name")
    private String name;

    // NUEVO: código de 6 caracteres para invitar usuarios al grupo
    @Column(name = "invite_code", unique = true)
    private String inviteCode;

    @OneToMany(mappedBy = "group")
    private List<GroupMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private List<Expense> expenses = new ArrayList<>();

    public Group(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.name = name;
        this.members = new ArrayList<>();
        this.expenses = new ArrayList<>();
        // Genera un código único de 6 caracteres al crear el grupo
        this.inviteCode = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    public Group() {}

    public void addMember(GroupMember member) {
        if (!this.members.contains(member)) {
            this.members.add(member);
        }
    }

    public void deleteMember(GroupMember member) {
        this.members.remove(member);
    }

    public void addExpense(Expense expense) {
        this.expenses.add(expense);
    }

    public String getName()       { return name; }
    public String getInviteCode() { return inviteCode; }
    public List<GroupMember> getMembers()  { return members; }
    public List<Expense>     getExpenses() { return expenses; }
}
