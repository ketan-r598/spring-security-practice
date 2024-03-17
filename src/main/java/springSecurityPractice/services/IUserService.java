package springSecurityPractice.services;

import java.util.Optional;

import springSecurityPractice.dtos.UserRequestDTO;
import springSecurityPractice.dtos.UserResponseDTO;
import springSecurityPractice.exceptions.UserAlreadyExistException;
import springSecurityPractice.exceptions.UserNotFoundException;

public interface IUserService {
	
	public Optional<UserResponseDTO> saveUser(UserRequestDTO userRequestDTO) throws UserAlreadyExistException;
	public Optional<UserResponseDTO> getUser(String email) throws UserNotFoundException;
	
}
