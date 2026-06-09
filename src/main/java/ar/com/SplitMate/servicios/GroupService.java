
package ar.com.splitmate.servicios;

import ar.com.splitmate.Group;
import ar.com.splitmate.GroupMember;

import java.util.List;


public interface GroupService {
    void guardarGrupo(Group group);
    
    public void agregarMiembro(GroupMember usuario);
    
    public Group buscarPorId(Long id);

    Group buscarPorCodigo(String inviteCode);

    List<Group> buscarGruposDeUsuario(Long userId);
    List<Group> listAll();

}
