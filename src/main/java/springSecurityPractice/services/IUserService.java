package springSecurityPractice.services;

import java.util.Optional;

import springSecurityPractice.dtos.UserRequestDTO;
import springSecurityPractice.dtos.UserResponseDTO;
import springSecurityPractice.exceptions.UserAlreadyExistException;
import springSecurityPractice.exceptions.UserNotFoundException;
import springSecurityPractice.models.PasswordResetToken;
import springSecurityPractice.models.User;
import springSecurityPractice.models.VerificationToken;

public interface IUserService {
	
	public Optional<UserResponseDTO> saveUser(UserRequestDTO userRequestDTO) throws UserAlreadyExistException;
	public Optional<UserResponseDTO> getUser(String email) throws UserNotFoundException;
	public User getUserUser(String email);
	public void saveRegisteredUser(User user);
	
	public void createVerificationToken(User user, String token);
	public VerificationToken getVerificationToken(String verificationToken);
	
	public void createPasswordResetToken(User user, String token);
	public PasswordResetToken getPasswordResetToken(String passwordResetToken);
	
}
