import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sharafi.Application;
import sharafi.advice.CustomException;
import sharafi.advice.TransactionException;
import sharafi.model.Account;
import sharafi.model.Gender;
import sharafi.model.MilitaryServiceStatus;
import sharafi.model.Person;
import sharafi.repository.AccountRepository;
import sharafi.repository.PersonRepository;
import sharafi.service.AccountService;
import sharafi.service.PersonService;
import sharafi.service.TransactionService;

@SpringBootTest(classes = Application.class)
public class MainTest {

	@Autowired
	PersonService personService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	TransactionService transactionService;
	
	@Test
	public void saveAccountTest() throws CustomException {
		Assertions.assertThrows(CustomException.class, () -> accountService.getAccount(5));
		Account account = new Account(5, "4632235", 2500000L, null, "IR123456789098765432105501", personRepository.getReferenceById(5), null);
		accountService.saveAccount(account);
		Assertions.assertNotNull(accountService.getAccount(5));
	}
	
	@Test
	public void updatePersonTest() throws CustomException {
		Assertions.assertNotEquals(personRepository.findById(1).get().getPhoneNumber(), "09217778998");
		Person person = new Person(1, "1234567890", "09217778998", "Parsa", "Sharafi", LocalDate.of(2000, 05, 20), 
				Gender.مرد, MilitaryServiceStatus.معاف, "parsa@gmail.com", null, null);
		personService.updatePerson(person);
		Assertions.assertEquals(personRepository.findById(1).get().getPhoneNumber(), "09217778998");
	}
	
	@Test
	public void successfulTransactionTest() throws CustomException, TransactionException {
		Assertions.assertEquals(accountRepository.findById(4).get().getBalance(), 20_000_000L);
		transactionService.saveTransaction(15_000_000L, 4);
		Assertions.assertEquals(accountRepository.findById(4).get().getBalance(), 35_000_000L);
	}
	
	@Test
	public void transactionDailyLimitTest() throws CustomException, TransactionException {
		transactionService.saveTransaction(-9_500_000L, 3);
		Assertions.assertThrows(TransactionException.class, () -> transactionService.saveTransaction(-700_000L, 3));
	}
	
	@Test
	public void accountBalanceLimitTest() {
		Assertions.assertEquals(accountRepository.findById(2).get().getBalance(), 43_324L);
		Assertions.assertThrows(TransactionException.class, () -> transactionService.saveTransaction(-40_000L, 2));
		Assertions.assertEquals(accountRepository.findById(2).get().getBalance(), 43_324L);
	}
	
}
