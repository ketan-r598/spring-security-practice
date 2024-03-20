package springSecurityPractice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import springSecurityPractice.dtos.UserRequestDTO;
import springSecurityPractice.dtos.UserResponseDTO;
import springSecurityPractice.exceptions.UserAlreadyExistException;
import springSecurityPractice.exceptions.UserNotFoundException;
import springSecurityPractice.models.User;
import springSecurityPractice.models.VerificationToken;
import springSecurityPractice.repositories.UserRepository;
import springSecurityPractice.repositories.VerificationTokenRepository;

@Service
@Primary
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private VerificationTokenRepository tokenRepository;
	
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
		
		userResponseDTO.setFirstName(newUser.getFirstName());
		userResponseDTO.setLastName(newUser.getLastName());
		userResponseDTO.setEmail(newUser.getEmail());
		
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

	@Override
	public void saveRegisteredUser(User user) {
		// TODO Auto-generated method stub
		userRepository.save(user);
		
	}

	@Override
	public void createVerificationToken(User user, String token) {
		VerificationToken myToken = new VerificationToken();
		
		myToken.setToken(token);
		myToken.setUser(user);
		myToken.setExpiryDate();
		
		tokenRepository.save(myToken);
	}

	@Override
	public VerificationToken getVerificationToken(String verificationToken) {
		return tokenRepository.findByToken(verificationToken);
	}

	@Override
	public User getUserUser(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email).get();
	}
}
