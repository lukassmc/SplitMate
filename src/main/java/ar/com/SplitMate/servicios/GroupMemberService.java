
package ar.com.splitmate.servicios;

import ar.com.splitmate.GroupMember;


public interface GroupMemberService {
    void guardarMiembro(GroupMember miembro);
    
    GroupMember obtenerMiembro(Long userId, Long groupId);
}
