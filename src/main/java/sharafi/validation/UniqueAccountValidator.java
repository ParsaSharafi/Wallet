package sharafi.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import sharafi.model.Account;
import sharafi.service.AccountService;

@Configuration
public class UniqueAccountValidator implements ConstraintValidator<UniqueAccountValidation, Account> {
	
	private static final UniqueAccountValidator holder = new UniqueAccountValidator();
	private AccountService accountService;
	
	@Bean
	static UniqueAccountValidator getUniqueAccountValidator(AccountService accountService) {
		holder.accountService = accountService;
		return holder;
	}
	
	@Override
	public boolean isValid(Account account, ConstraintValidatorContext context) {
		
		Map<String , String> uniqueValues = new HashMap<>();
		uniqueValues.put("accountNumber", account.getAccountNumber());
		uniqueValues.put("iban", account.getIban());
		
		String message = holder.accountService.checkUniqueness(account.getId(), uniqueValues);
		
		if ("".equals(message))
			return true;
		
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		
		return false;
	}
}
