package springSecurityPractice.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import springSecurityPractice.dtos.UserRequestDTO;
import springSecurityPractice.dtos.UserResponseDTO;
import springSecurityPractice.exceptions.UserAlreadyExistException;
import springSecurityPractice.services.IUserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserRegistrationController {
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid UserRequestDTO userRequestDTO, Errors error) {
		
		if(error.hasErrors()) {
			System.out.println(error.getAllErrors());
//			System.out.println(error.getFieldError());
//			Multimap<String, String> errorMap = null;
			return new ResponseEntity<UserResponseDTO>(null,null, HttpStatus.BAD_REQUEST);
		}
		
		Optional<UserResponseDTO> userResponseDTO;
		try {
			userResponseDTO = userService.saveUser(userRequestDTO);
		} catch(UserAlreadyExistException ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserResponseDTO>(userResponseDTO.get(), HttpStatus.CREATED);
	}
}