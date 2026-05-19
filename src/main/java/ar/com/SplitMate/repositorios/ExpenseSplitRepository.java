package ar.com.splitmate.repositorios;

import ar.com.splitmate.ExpenseSplit;
import ar.com.splitmate.Group;
import ar.com.splitmate.GroupMember;
import ar.com.splitmate.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseSplitRepository extends JpaRepository<ExpenseSplit, Long> {

    List<ExpenseSplit> findByMember(GroupMember member);

}
