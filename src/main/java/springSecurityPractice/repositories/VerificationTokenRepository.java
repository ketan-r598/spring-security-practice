package springSecurityPractice.repositories;

import org.springframework.data.repository.CrudRepository;

import springSecurityPractice.models.User;
import springSecurityPractice.models.VerificationToken;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {
	
	VerificationToken findByToken(String token);
	VerificationToken findByUser(User user);
}
