package springSecurityPractice.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import springSecurityPractice.models.User;


public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByEmail(String email);
}
