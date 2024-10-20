package sharafi.service;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import sharafi.advice.CustomException;
import sharafi.model.Account;
import sharafi.model.Transaction;
import sharafi.repository.AccountRepository;

@Service
public class AccountService {

	private final AccountRepository accountRepository;
	private final PersonService personService;
	private final ApplicationContext context;
	
	public AccountService(AccountRepository accountRepository, PersonService personService, ApplicationContext context) {
		this.accountRepository = accountRepository;
		this.personService = personService;
		this.context = context;
	}
	
	public boolean getAccountExistence(int accountId) {
		return accountRepository.existsById(accountId);
	}
	
	public Account getAccountReference(int accountId) {
		return accountRepository.getReferenceById(accountId);
	}
	
	public Account getAccount(int accountId) throws CustomException {
		return accountRepository.findById(accountId).orElseThrow(() -> new CustomException("no account found with id: " + accountId));
	}
	
	public List<Account> getAccounts() {
		return accountRepository.findAll();
	}
	
	@Transactional(rollbackOn = Exception.class)
	public void saveAccount(Account account) throws CustomException {
		
		int personId = account.getId();
		
		if (getAccountExistence(personId))
			throw new CustomException("owner already has an account");
		
		if (! personService.getPersonExistence(personId))
			throw new CustomException("no person found with ownerId: " + personId);
		
		account.setPerson(personService.getPersonReference(personId));
		
		accountRepository.save(account);
		
		context.getBean(TransactionService.class).saveTransaction
			(new Transaction(accountRepository.getReferenceById(personId), account.getBalance(), true));	
	}
	
	public void updateAccount(Account account) throws CustomException {
		
		int accountId = account.getId();
		if (! getAccountExistence(accountId))
			throw new CustomException("no account found with id: " + accountId);
		
		accountRepository.save(account);
	}
	
	public void deleteAccount(int accountId) throws CustomException {
		
		if (! getAccountExistence(accountId))
			throw new CustomException("no account found with id: " + accountId);
		
		accountRepository.deleteById(accountId);
	}

	public String checkUniqueness(int id, Map<String, String> values) {
		
		StringBuilder stringBuilder = new StringBuilder();
		String accountNumber = values.get("accountNumber");
		String iban = values.get("iban");
		
		if (accountRepository.existsByAccountNumber(accountNumber) && accountRepository.findByAccountNumber(accountNumber).getId() != id)
			stringBuilder.append(" - شماره‌حساب یکتا نیست - ");
		
		if (accountRepository.existsByIban(iban) && accountRepository.findByIban(iban).getId() != id)
			stringBuilder.append(" - شبا یکتا نیست - ");
		
		return stringBuilder.toString();
	}
}