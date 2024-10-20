package sharafi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sharafi.model.Transaction;

@Repository
public interface TransactionRepository  extends JpaRepository<Transaction, Integer> {
	
	List<Transaction> findByAccountIdOrderByDateDescTimeDesc(Integer accountId);
	
	@Query(value = "SELECT SUM (signed_amount), FROM transaction where signed_amount < 0 AND successful = true "
			+ "GROUP BY account_person_id, date having account_person_id = ?1 AND date = current_date()", nativeQuery = true)
	Long findWithdrawalSum(Integer accountId);
}
