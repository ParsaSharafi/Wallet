package sharafi.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import sharafi.advice.CustomException;
import sharafi.model.Account;
import sharafi.model.CustomUser;
import sharafi.model.LoggedInInfoDto;
import sharafi.model.Person;
import sharafi.repository.PersonRepository;

@Service
public class PersonService {

	private final PersonRepository personRepository;
	private BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder(12);
	
	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
	
	public LoggedInInfoDto getLoggedInInfo(String username) {
		
		Person person = personRepository.findByUsername(username);
		if (person == null)
			return null;
		
		Account account = person.getAccount();
		if (account == null)
			return null;
		
		return new LoggedInInfoDto(person.getFirstName() + " " + person.getLastName(), account.getAccountNumber(), account.getBalance());
	}
	
	public boolean getPersonExistence(int personId) {
		return personRepository.existsById(personId);
	}
	
	public Person getPersonReference(int personId) {
		return personRepository.getReferenceById(personId);
	}
	
	public int getPerson(String username) {
		Integer id = personRepository.findIdByUsername(username);
		return Optional.ofNullable(id).isPresent() ? id : 0;
	}
	
	public Person getPerson(int personId) {
		return personRepository.findById(personId).orElse(null);
	}
	
	public List<Person> getPersons() {
		return personRepository.findAll();
	}
	
	public void savePerson(Person person) throws CustomException {
		
		if (person.getId() != 0)
			throw new CustomException("cannot choose person id for creation");
		
		person.setUser(new CustomUser(person.getNationalId(), passEncoder.encode(person.getPhoneNumber())));
		
		personRepository.save(person);
	}
	
	public void updatePerson(Person person) throws CustomException {
		
		int personId = person.getId();
		if(! getPersonExistence(personId))
			throw new CustomException("no person found with id: " + personId);
		
		personRepository.save(person);
	}
	
	public void deletePerson(int personId) throws CustomException {
		
		if (! getPersonExistence(personId))
			throw new CustomException("no person found with id: " + personId);
		
		personRepository.deleteById(personId);
	}

	public String checkUniqueness(int id, Map<String, String> values) {
		
		StringBuilder stringBuilder = new StringBuilder();
		String nationalId = values.get("nationalId");
		String phoneNumber = values.get("phoneNumber");
		String email = values.get("email");
		
		if (personRepository.existsByNationalId(nationalId) && personRepository.findByNationalId(nationalId).getId() != id)
			stringBuilder.append(" - کد ملی یکتا نیست - ");
		
		if (personRepository.existsByPhoneNumber(phoneNumber) && personRepository.findByPhoneNumber(phoneNumber).getId() != id)
			stringBuilder.append(" - شماره موبایل یکتا نیست - ");
		
		if (personRepository.existsByEmail(email) && personRepository.findByEmail(email).getId() != id)
			stringBuilder.append(" - ایمیل یکتا نیست - ");
		
		return stringBuilder.toString();
	}
}