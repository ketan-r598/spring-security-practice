package springSecurityPractice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import springSecurityPractice.dtos.UserRequestDTO;
import springSecurityPractice.dtos.UserResponseDTO;
import springSecurityPractice.exceptions.UserAlreadyExistException;
import springSecurityPractice.exceptions.UserNotFoundException;
import springSecurityPractice.models.User;
import springSecurityPractice.repositories.UserRepository;

@Service
@Primary
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public Optional<UserResponseDTO> saveUser(UserRequestDTO userRequestDTO) throws UserAlreadyExistException {
		
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
		
		UserResponseDTO userResponseDTO = new UserResponseDTO(); 
		
		return Optional.of(userResponseDTO);
	}

	@Override
	public Optional<UserResponseDTO> getUser(String email) throws UserNotFoundException {
		
		Optional<User> user = userRepository.findByEmail(email);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("user" + email + "does not exists");
		}
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		
		userResponseDTO.setFirstName(user.get().getFirstName());
		userResponseDTO.setLastName(user.get().getLastName());
		userResponseDTO.setEmail(user.get().getEmail());
		
		return Optional.of(userResponseDTO);
	}

}
