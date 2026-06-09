
package ar.com.splitmate.servicios;

import ar.com.splitmate.Group;
import ar.com.splitmate.GroupMember;
import ar.com.splitmate.repositorios.GroupMemberRepository;
import ar.com.splitmate.repositorios.GroupRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class GroupServiceImp implements GroupService {

    private final GroupRepository repository;

    private final GroupMemberRepository memberRepository;

    public GroupServiceImp(GroupRepository repository,
                           GroupMemberRepository memberRepository) {
        this.repository = repository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void guardarGrupo(Group group) {
        repository.save(group);
    }

    @Override
    public void agregarMiembro(GroupMember member) {
        this.memberRepository.save(member);
}    
   
    @Override
    public Group buscarPorId(Long Id){
        return repository.findById(Id).orElseThrow(()-> new RuntimeException("Grupo no encontrado" + Id));
        
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

    @Override
    public List<Group> listAll(){
        return repository.findAll();
    }
}