package springSecurityPractice.customValidators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import springSecurityPractice.dtos.UserRequestDTO;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRequestDTO> {

	public void initialize(PasswordMatches constraintAnnotation) {}
	
	@Override
	public boolean isValid(UserRequestDTO userRequestDTO, ConstraintValidatorContext context) {
		return userRequestDTO.getPassword().equals(userRequestDTO.getMatchingPassword());
	}

}
