package sharafi.validation;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import com.github.eloyzone.jalalicalendar.DateConverter;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import sharafi.model.Gender;
import sharafi.model.Person;

public class MilitaryServiceValidator implements ConstraintValidator<MilitaryServiceValidation, Person> {
	
	@Override
	public boolean isValid(Person person, ConstraintValidatorContext context) {
		
		String birthDate = person.getBirthDate();
		LocalDate date;
		
		try {
			String[] dateValues = birthDate.split("[-]");
			date = new DateConverter().jalaliToGregorian(Integer.valueOf(dateValues[0]), Integer.valueOf(dateValues[1]), Integer.valueOf(dateValues[2]));
		} catch (Exception e) {
			return false;
		}
		
		return Gender.زن.equals(person.getGender()) ||
				Period.between(Optional.ofNullable(date).isPresent() ? date : LocalDate.MIN, LocalDate.now()).getYears() < 18 ||
				person.getMilitaryServiceStatus() != null;
	}
	
}
