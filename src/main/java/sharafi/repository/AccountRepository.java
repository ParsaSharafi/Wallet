package sharafi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sharafi.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	boolean existsByAccountNumber(String accountNumber);
	
	Account findByAccountNumber(String accountNumber);
	
	boolean existsByIban(String iban);
	
	Account findByIban(String iban);
}