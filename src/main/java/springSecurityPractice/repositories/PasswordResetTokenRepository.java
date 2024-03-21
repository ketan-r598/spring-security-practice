package springSecurityPractice.repositories;

import org.springframework.data.repository.CrudRepository;

import springSecurityPractice.models.PasswordResetToken;
import springSecurityPractice.models.User;



public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {
	
	PasswordResetToken findByToken(String token);
	PasswordResetToken findByUser(User user);
}
