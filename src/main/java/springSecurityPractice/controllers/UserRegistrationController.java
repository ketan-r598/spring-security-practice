package springSecurityPractice.controllers;

import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import springSecurityPractice.dtos.UserRequestDTO;
import springSecurityPractice.dtos.UserResponseDTO;
import springSecurityPractice.events.OnRegistrationCompleteEvent;
import springSecurityPractice.exceptions.UserAlreadyExistException;
import springSecurityPractice.models.User;
import springSecurityPractice.models.VerificationToken;
import springSecurityPractice.repositories.VerificationTokenRepository;
import springSecurityPractice.services.IUserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserRegistrationController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private VerificationTokenRepository tokenRepo;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid UserRequestDTO userRequestDTO, Errors error, HttpServletRequest request) {
		
		if(error.hasErrors()) {
			System.out.println(error.getAllErrors());
//			System.out.println(error.getFieldError());
//			Multimap<String, String> errorMap = null;
			return new ResponseEntity<UserResponseDTO>(null,null, HttpStatus.BAD_REQUEST);
		}
		
		Optional<UserResponseDTO> userResponseDTO;
		try {
			userResponseDTO = userService.saveUser(userRequestDTO);
			if(userResponseDTO.isPresent()) {
				User _user = userService.getUserUser(userResponseDTO.get().getEmail());
				System.out.println(_user);
				eventPublisher.publishEvent(new OnRegistrationCompleteEvent(_user, request.getLocale(), request.getContextPath()));
				System.out.println("\nAfter the event publisher\n");
			} else {
				// TODO::
			}
			
		} catch(UserAlreadyExistException ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch(RuntimeException ex) {
			System.out.println(ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<UserResponseDTO>(userResponseDTO.get(), HttpStatus.CREATED);
	}
	
	@GetMapping("/registrationConfirm")
	public ResponseEntity<?> confirmRegistration(WebRequest request, @RequestParam("token") String token) {
		
		Locale locale = request.getLocale();
		VerificationToken verificationToken = userService.getVerificationToken(token);
		
		if(verificationToken == null || verificationToken.isValid() == false) {
			return new ResponseEntity<String>("Invalid Token", HttpStatus.UNAUTHORIZED);
		}
		
		User user = verificationToken.getUser();
		
		Calendar calendar = Calendar.getInstance();
		
		if((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
			return new ResponseEntity<String>("Token has expired", HttpStatus.BAD_REQUEST);
		}
		
		user.setEnabled(true);
		verificationToken.setValid(false);
		
		userService.saveRegisteredUser(user);
		tokenRepo.save(verificationToken);
		return new ResponseEntity<String>("Token Verified and Account is activated",HttpStatus.ACCEPTED);
	}
}


//System.out.println("\n");
//
//System.out.println("Servlet Context = "+ request.getServletContext());
//System.out.println("Auth Type = " + request.getAuthType());
//
//System.out.println("Local Addr = " + request.getLocalAddr());
//System.out.println("Local Name = " + request.getLocalName());
//System.out.println("Local Port" + request.getLocalPort());
//
//System.out.println("Protocol = "+ request.getProtocol());
//System.out.println("Protocol Request Id = " + request.getProtocolRequestId());
//
//System.out.println("Query String = " + request.getQueryString());
//
//System.out.println("Remote Addr = " + request.getRemoteAddr());
//System.out.println("Remote Host = " + request.getRemoteHost());
//System.out.println("Remote Port = " + request.getRemotePort());
//System.out.println("Remote User = " + request.getRemoteUser());
//
//System.out.println("Requested Seesion Id = " + request.getRequestedSessionId());
//System.out.println("Request Id = " + request.getRequestId());
//System.out.println("Request URI = " + request.getRequestURI());
//
//System.out.println("Server Name = " + request.getServerName());
//System.out.println("Server Port = " + request.getServerPort());
//System.out.println("Servlet Path = " + request.getServletPath());
//
//System.out.println("Request URL = " + request.getRequestURL());
//
//System.out.println("Cookies = " + request.getCookies().toString());
//System.out.println("Header Names = " + request.getHeaderNames());
//
//System.out.println("HttpServletMapping = " + request.getHttpServletMapping());
//
//System.out.println("Locale = " + request.getLocale());
//System.out.println("Locales = ");
//
//Iterator<Locale> itr = request.getLocales().asIterator();
//
//while(itr.hasNext()) {
//	System.out.println();
//	System.out.println(itr.next());
//}
//
//System.out.println("ParameterMap = " + request.getParameterMap());
//System.out.println("Parameter Names = " + request.getParameterNames());
//
//System.out.println("Servlet Connection = " + request.getServletConnection());
//
//System.out.println("Servlet Context = " + request.getServletContext());
//System.out.println("Session = " + request.getSession().toString());
//System.out.println("Trailer Feilds = " + request.getTrailerFields());
//System.out.println("User Principal = " + request.getUserPrincipal());
//
//System.out.println("\n"); 