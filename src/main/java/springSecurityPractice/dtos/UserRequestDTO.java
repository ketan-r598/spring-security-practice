package springSecurityPractice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springSecurityPractice.customValidators.PasswordMatches;
import springSecurityPractice.customValidators.ValidEmail;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class UserRequestDTO {
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotBlank
	private String password;
	private String matchingPassword;
	
	@NotBlank
	@ValidEmail
	private String email;
}
