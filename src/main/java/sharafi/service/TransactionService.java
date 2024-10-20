package sharafi.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import sharafi.advice.CustomException;
import sharafi.advice.TransactionException;
import sharafi.model.Account;
import sharafi.model.Transaction;
import sharafi.repository.TransactionRepository;

@Service
public class TransactionService {

	Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	private final TransactionRepository transactionRepository;
	private final AccountService accountService;
	
	public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
		this.transactionRepository = transactionRepository;
		this.accountService = accountService;
	}
	
	public long getWithdrawalSum(int accountId) {
		Long withdrawalSum = transactionRepository.findWithdrawalSum(accountId);
		return Optional.ofNullable(withdrawalSum).isPresent() ? withdrawalSum : 0;
	}
	
	public List<Transaction> getTransactions(int accountId) {
		return transactionRepository.findByAccountIdOrderByDateDescTimeDesc(accountId);
	}
	
	@Transactional(rollbackOn = Exception.class)
	public void saveTransaction(long signedAmount, int accountId) throws CustomException, TransactionException {
		
		Account account = (Account) Hibernate.unproxy(accountService.getAccount(accountId));
		
		Transaction transaction = new Transaction(account, signedAmount, true);
		Set<ConstraintViolation<Transaction>> transactionViolations = validator.validate(transaction);
		if (! transactionViolations.isEmpty())
			throw new TransactionException(transactionViolations.stream().
					map(ConstraintViolation<Transaction>::getMessage).collect(Collectors.joining("\n")), accountId, signedAmount);
		
		account.setBalance(account.getBalance() + signedAmount);
		Set<ConstraintViolation<Account>> accountViolations = validator.validate(account);
		if (! accountViolations.isEmpty())
			throw new TransactionException(accountViolations.stream().
					map(ConstraintViolation<Account>::getMessage).collect(Collectors.joining("\n")), accountId, signedAmount);
		
		accountService.updateAccount(account);
		transactionRepository.save(transaction);
	}
	
	public void saveTransaction(Transaction transaction) {
		transactionRepository.save(transaction);
	}
}