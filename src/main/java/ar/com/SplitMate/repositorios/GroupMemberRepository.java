
package ar.com.splitmate.repositorios;

import ar.com.splitmate.GroupMember;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    
    Optional<GroupMember> findByUserIdAndGroupId(Long userId, Long groupId);
}
