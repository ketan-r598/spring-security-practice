package springSecurityPractice.services;

import java.util.Optional;

import springSecurityPractice.dtos.UserRequestDTO;
import springSecurityPractice.exceptions.UserAlreadyExistException;
import springSecurityPractice.exceptions.UserNotFoundException;
import springSecurityPractice.models.User;

public interface IUserService {
	
	public Optional<User> saveUser(UserRequestDTO userRequestDTO) throws UserAlreadyExistException;
	public Optional<User> getUser(String email) throws UserNotFoundException;
	
}
