package springSecurityPractice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import springSecurityPractice.customValidators.ValidEmail;

@Data
@AllArgsConstructor
@Entity(name = "users")
public class User extends BaseModel{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long user_id;
	
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotBlank
	private String password;
	
	private boolean enabled;
	
	@ValidEmail
	private String email;
	
	public User() {
		super();
		this.enabled = false;
	}
}
