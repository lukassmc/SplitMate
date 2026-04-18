
package ar.com.splitmate.repositorios;

import ar.com.splitmate.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>{
    
}
