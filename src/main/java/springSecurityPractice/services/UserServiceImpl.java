package springSecurityPractice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import springSecurityPractice.dtos.UserRequestDTO;
import springSecurityPractice.exceptions.UserAlreadyExistException;
import springSecurityPractice.exceptions.UserNotFoundException;
import springSecurityPractice.models.User;
import springSecurityPractice.repositories.UserRepository;

public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Optional<User> saveUser(UserRequestDTO userRequestDTO) throws UserAlreadyExistException {
		
		Optional<User> user = userRepository.findByEmail(userRequestDTO.getEmail());
		
		if(user.isPresent()) {
			throw new UserAlreadyExistException("User Already Exists");
		}
		
		User newUser = new User();
		
		newUser.setFirstName(userRequestDTO.getFirstName());
		newUser.setLastName(userRequestDTO.getLastName());
		newUser.setEmail(userRequestDTO.getEmail());
		newUser.setPassword(encoder.encode(userRequestDTO.getPassword()));
		
		newUser = userRepository.save(newUser);
		System.out.println(newUser);
		
		return Optional.of(newUser);
	}

	@Override
	public Optional<User> getUser(String email) throws UserNotFoundException {
		
		return Optional.empty();
	}

}
