package sharafi.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import sharafi.advice.CustomException;
import sharafi.model.Person;
import sharafi.model.ResponseDto;
import sharafi.service.PersonService;

@RestController
@RequestMapping("/persons")
@CrossOrigin
public class PersonController {
	
	private final PersonService personService;
	
	public PersonController(PersonService personService) {
		this.personService = personService;
	}
	
	//Create**********
	@PostMapping
	public ResponseDto addPerson(@Valid @RequestBody Person person) throws CustomException {
		personService.savePerson(person);
		return new ResponseDto(true, "شخص جدید ساخته‌شد");
	}
	
	//Read**********
	@GetMapping
	public ResponseDto showPersons() {
		return new ResponseDto(true, personService.getPersons());
	}
	
	@GetMapping("/{personId}")
	public ResponseDto showPerson(@PathVariable int personId) throws CustomException {
		return new ResponseDto(true, personService.getPerson(personId));
	}
	
	//information of logged in user to see transactions
	@GetMapping("/info")
	public ResponseDto showLoggedIn(Principal principal) throws CustomException {
		if (principal == null)
			throw new CustomException("not authorized");
		return new ResponseDto(true, personService.getLoggedInInfo(principal.getName()));
	}
	
	//Update**********
	@PutMapping
	public ResponseDto updatePerson(@Valid @RequestBody Person person) throws CustomException {
		personService.updatePerson(person);
		return new ResponseDto(true, "شخص به‌روزرسانی شد");
	}
	
	//Delete**********
	@DeleteMapping("/{personId}")
	public ResponseDto deletePerson(@PathVariable int personId) throws CustomException {
		personService.deletePerson(personId);
		return new ResponseDto(true, "شخص حذف شد");
	}
}
