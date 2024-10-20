package sharafi.validation;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import sharafi.model.Transaction;
import sharafi.service.TransactionService;

@Configuration
public class SignedAmountValidator implements ConstraintValidator<SignedAmountValidation, Transaction> {

	private static final SignedAmountValidator holder = new SignedAmountValidator();
	private TransactionService transactionService;
	
	@Bean
	static SignedAmountValidator getSignedAmountValidator(TransactionService transactionService) {
		holder.transactionService = transactionService;
		return holder;
	}
	
	@Override
	public boolean isValid(Transaction transaction, ConstraintValidatorContext context) {
		long withdrawalSum = holder.transactionService.getWithdrawalSum(transaction.getAccount().getId());
		return transaction.getSignedAmount() > 0 || (transaction.getSignedAmount() <= -100_000 &&
				transaction.getSignedAmount() + (Optional.ofNullable(withdrawalSum).isPresent() ? withdrawalSum : 0) >= -10_000_000);
	}
}