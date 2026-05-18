
package ar.com.splitmate.repositorios;

import ar.com.splitmate.GroupMember;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {


    GroupMember findByUserIdAndGroupId(Long userId, Long groupId);


    List<GroupMember> findByUserId(Long userId);
}
