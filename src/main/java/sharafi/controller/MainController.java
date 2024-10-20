package sharafi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sharafi.advice.CustomException;
import sharafi.model.CustomUser;
import sharafi.model.ResponseDto;
import sharafi.service.CustomUserService;

@RestController
@RequestMapping("/")
@CrossOrigin
public class MainController {
	
	CustomUserService customUserService;
	
	public MainController(CustomUserService customUserService) {
		this.customUserService = customUserService;
	}
	
	@PostMapping("login")
    public ResponseDto login(@RequestBody CustomUser customUser) throws CustomException {
        return new ResponseDto(true, customUserService.verify(customUser));
    }
	
	@RequestMapping(value = "**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
	public void wrongURL() throws CustomException {
		throw new CustomException("no matching url found");
	}
}
