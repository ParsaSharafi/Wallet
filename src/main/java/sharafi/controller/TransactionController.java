package sharafi.controller;

import java.security.Principal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sharafi.advice.CustomException;
import sharafi.advice.TransactionException;
import sharafi.model.ResponseDto;
import sharafi.service.PersonService;
import sharafi.service.TransactionService;

@RestController
@RequestMapping("/transactions")
@CrossOrigin
public class TransactionController {
	
	private final TransactionService transactionService;
	private final PersonService personService;
	
	public TransactionController (TransactionService transactionService, PersonService personService) {
		this.transactionService = transactionService;
		this.personService = personService;
	}

	@GetMapping
	public ResponseDto showTransactions(Principal principal) throws CustomException {
		if (principal == null)
			throw new CustomException("not authorized");
		return new ResponseDto(true, transactionService.getTransactions(personService.getPerson(principal.getName())));
	}
	
	@PostMapping("/{signedAmount}")
	public ResponseDto addTransaction(Principal principal, @PathVariable long signedAmount) throws CustomException, TransactionException {
		if (principal == null)
			throw new CustomException("not authorized");
		transactionService.saveTransaction(signedAmount, personService.getPerson(principal.getName()));
		return new ResponseDto(true, "تراکنش موفقیت‌آمیز بود");
	}
}