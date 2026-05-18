
package ar.com.splitmate.servicios;

import ar.com.splitmate.Group;
import ar.com.splitmate.GroupMember;
import ar.com.splitmate.User;
import ar.com.splitmate.repositorios.GroupMemberRepository;
import ar.com.splitmate.repositorios.GroupRepository;
import ar.com.splitmate.repositorios.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class GroupServiceImp implements GroupService {

    private final GroupRepository repository;
    private final UserRepository userRepository;
    private final GroupMemberRepository memberRepository;

    public GroupServiceImp(GroupRepository repository,
                           UserRepository userRepository,
                           GroupMemberRepository memberRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void guardarGrupo(Group group) {
        repository.save(group);
    }
 /*   
    @Override
    public void agregarMiembro(Long userId, Long groupId, String rol) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User no encontrado"));

    Group group = repository.findById(groupId)
        .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

    GroupMember member = new GroupMember(user, group);

    memberRepository.save(member);
}
*/
    @Override
    public void agregarMiembro(GroupMember member) {
        this.memberRepository.save(member);
}    
   
    @Override
    public Group buscarPorId(Long Id){
        return repository.findById(Id).orElseThrow(()-> new RuntimeException("Grupo no encontrado"));
        
    }
    
    @Override
    public Group buscarPorCodigo(String inviteCode){
        return repository.findByInviteCode(inviteCode.toUpperCase());

    }

    @Override
    public List<Group> buscarGruposDeUsuario(Long userId) {
        return memberRepository.findByUserId(userId)
                .stream()
                .map(GroupMember :: getGroup)
                .collect(Collectors.toList());


    }
}