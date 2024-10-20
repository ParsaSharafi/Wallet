package sharafi.controller;

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
import sharafi.model.Account;
import sharafi.model.ResponseDto;
import sharafi.service.AccountService;

@RestController
@RequestMapping("/accounts")
@CrossOrigin
public class AccountController {
	
	private final AccountService accountService;
	
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	//Create**********
	@PostMapping()
	public ResponseDto addAccount(@Valid @RequestBody Account account) throws CustomException {
		accountService.saveAccount(account);
		return new ResponseDto(true, "حساب جدید ساخته‌شد");
	}
	
	//Read**********
	@GetMapping
	public ResponseDto showAccounts() {
		return new ResponseDto(true, accountService.getAccounts());
	}
	
	@GetMapping("/{accountId}")
	public ResponseDto showAccount(@PathVariable int accountId) throws CustomException {
		return new ResponseDto(true, accountService.getAccount(accountId));
	}
	
	//Update**********
	@PutMapping()
	public ResponseDto updateAccount(@Valid @RequestBody Account account) throws CustomException {
		accountService.updateAccount(account);
		return new ResponseDto(true, "حساب به‌روزرسانی شد");
	}
	
	//Delete**********
	@DeleteMapping("/{accountId}")
	public ResponseDto deleteAccount(@PathVariable int accountId) throws CustomException {
		accountService.deleteAccount(accountId);
		return new ResponseDto(true, "حساب حذف شد");
	}
}
