package sharafi.service;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sharafi.advice.CustomException;
import sharafi.model.CustomUser;
import sharafi.model.UserPrincipal;
import sharafi.repository.CustomUserRepository;

@Service
public class CustomUserService implements UserDetailsService {

	private CustomUserRepository CustomUserRepository;
	private ApplicationContext context;
	private JWTService jwtService;
	
	public CustomUserService(CustomUserRepository CustomUserRepository, ApplicationContext context, JWTService jwtService) {
		this.CustomUserRepository = CustomUserRepository;
		this.context = context;
		this.jwtService = jwtService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomUser customUser = CustomUserRepository.findById(username).
				orElseThrow(() -> new UsernameNotFoundException("no user found with username: " + username));
		return new UserPrincipal(customUser);
	}

	public String verify(CustomUser customUser) throws CustomException {
		Authentication authentication = context.getBean(AuthenticationManager.class).authenticate
				(new UsernamePasswordAuthenticationToken(customUser.getUsername(), customUser.getPassword()));
		
		if (authentication.isAuthenticated()) 
			return jwtService.generateToken(customUser.getUsername());
		else
			throw new CustomException("password is incorrect");
    }
}