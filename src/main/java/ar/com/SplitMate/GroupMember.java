
package ar.com.splitmate;

import ar.com.splitmate.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table( name = "group_member")
public class GroupMember extends Persistible{
    
    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private Group group;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public GroupMember(User user, Group group, Role role) {
        this.user = user;
        this.group = group;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public Group getGroup() {
        return group;
    }

    public Role getRole() {
        return role;
    }

    public GroupMember(User user, Group group) {
        this.user = user;
        this.group = group;
        this.role = role.MEMBER;
    }
    
    public GroupMember(){};
    
}
