package ar.com.splitmate.repositorios;

import ar.com.splitmate.ExpenseSplit;
import ar.com.splitmate.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseSplitRepository extends JpaRepository<ExpenseSplit, Long> {

    // Todos los splits de un grupo (para calcular balances)
    @Query("SELECT s FROM ExpenseSplit s WHERE s.expense.group.id = :groupId")
    List<ExpenseSplit> findByGroupId(@Param("groupId") Long groupId);

    // Splits pendientes de pago de un miembro en un grupo
    @Query("SELECT s FROM ExpenseSplit s WHERE s.expense.group.id = :groupId AND s.member.id = :memberId AND s.is_paid = false")
    List<ExpenseSplit> findPendingByGroupAndMember(@Param("groupId") Long groupId, @Param("memberId") Long memberId);

    // Splits de un gasto específico
    List<ExpenseSplit> findByExpenseId(Long expenseId);

    List<ExpenseSplit> findByMember(GroupMember member);

}
