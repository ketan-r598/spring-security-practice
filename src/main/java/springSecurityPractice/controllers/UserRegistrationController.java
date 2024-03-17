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
	public ResponseEntity<Object> register(@RequestBody @Valid UserRequestDTO userRequestDTO, Errors error) {
		Optional<UserResponseDTO> userResponseDTO;
		try {
			userResponseDTO = userService.saveUser(userRequestDTO);
		} catch(UserAlreadyExistException ex) {
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
	}
}