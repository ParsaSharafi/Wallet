package sharafi.advice;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ConstraintViolationException;
import sharafi.model.ResponseDto;
import sharafi.model.Transaction;
import sharafi.service.AccountService;
import sharafi.service.TransactionService;

@RestControllerAdvice
public class MainExceptionHandler {
	
	TransactionService transactionService;
	AccountService accountService;
	
	public MainExceptionHandler(TransactionService transactionService, AccountService accountService) {
		this.transactionService = transactionService;
		this.accountService = accountService;
	}
	
	Logger logger = LoggerFactory.getLogger(MainExceptionHandler.class);
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseDto onMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		
		Map<String, String> errors = new HashMap<>();
		ex.getGlobalErrors().forEach(e -> errors.put(e.getCode(), e.getDefaultMessage()));
		ex.getBindingResult().getFieldErrors().forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
		
		logger.info("Method Argument Not Valid Exception Thrown: " + errors);
		return new ResponseDto(false, errors);
	}
	
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseDto onConstraintViolationExceptions(ConstraintViolationException ex) {
		
    	Map<String, String> errors = new HashMap<>();
		ex.getConstraintViolations().forEach(e -> errors.put(e.getMessage(), (String) e.getInvalidValue()));
		
		logger.info("Constraint Violation Exception Thrown: " + errors);
        return new ResponseDto(false, errors);
    }
	
    @ExceptionHandler(UsernameNotFoundException.class)
	ResponseDto onUsernameNotFoundException(UsernameNotFoundException e) {
    	
    	Map<String, String> errors = new HashMap<>();
    	errors.put("error", e.getMessage());
		
		logger.info("Username Not Found Exception Thrown: " + e.getMessage());
		return new ResponseDto(false, errors);
	}
    
	@ExceptionHandler(TransactionException.class)
	ResponseDto onTransactionException(TransactionException e) {
		
		Map<String, String> errors = new HashMap<>();
		errors.put("error", e.getMessage());
		
		transactionService.saveTransaction(new Transaction(accountService.getAccountReference(e.getAccountId()), e.getSignedAmount(), false));
		
		logger.info("Transaction Exception Thrown: " + e.getMessage());
		return new ResponseDto(false, errors);
	}
	
	@ExceptionHandler(CustomException.class)
	ResponseDto onCustomException(CustomException e) {
		
		Map<String, String> errors = new HashMap<>();
		errors.put("error", e.getMessage());
		
		logger.info("Custom Exception Thrown: " + e.getMessage());
		return new ResponseDto(false, errors);
	}
}
