package sharafi.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import sharafi.model.Person;
import sharafi.service.PersonService;

@Configuration
public class UniquePersonValidator implements ConstraintValidator<UniquePersonValidation, Person> {
	
	private static final UniquePersonValidator holder = new UniquePersonValidator();
	private PersonService personService;
	
	@Bean
	static UniquePersonValidator getUniquePersonValidator(PersonService personService) {
		holder.personService = personService;
		return holder;
	}
	
	@Override
	public boolean isValid(Person person, ConstraintValidatorContext context) {
		
		Map<String , String> uniqueValues = new HashMap<>();
		uniqueValues.put("nationalId", person.getNationalId());
		uniqueValues.put("phoneNumber", person.getPhoneNumber());
		uniqueValues.put("email", person.getEmail());
		
		String message = holder.personService.checkUniqueness(person.getId(), uniqueValues);
		
		if ("".equals(message))
			return true;
		
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		
		return false;
	}
}
