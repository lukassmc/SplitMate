
package ar.com.SplitMate;



public class GroupMember {
    private User user;
    private Group group;
    private Role role;

    public GroupMember(User user, Group group, Role role) {
        this.user = user;
        this.group = group;
        this.role = role;
    }

    public GroupMember(User user, Group group) {
        this.user = user;
        this.group = group;
        this.role = role.MEMBER;
    }
    
}
