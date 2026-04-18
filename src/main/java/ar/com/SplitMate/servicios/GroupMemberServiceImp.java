
package ar.com.splitmate.servicios;

import ar.com.splitmate.GroupMember;
import ar.com.splitmate.repositorios.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupMemberServiceImp implements GroupMemberService{
    
    @Autowired
    private GroupMemberRepository repository;

    @Override
    public void guardarMiembro(GroupMember miembro) {
        this.repository.save(miembro);
    }
    
    @Override
    public GroupMember obtenerMiembro(Long userId, Long groupId) {
    return repository.findByUserIdAndGroupId(userId, groupId)
            .orElseThrow(() -> new RuntimeException("El usuario no pertenece al grupo"));
}
}
