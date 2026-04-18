
package ar.com.splitmate.servicios;

import ar.com.splitmate.Group;
import ar.com.splitmate.GroupMember;


public interface GroupService {
    void guardarGrupo(Group group);
    
    public void agregarMiembro(GroupMember usuario);
    
    public Group buscarPorId(Long id);
    
}
