package springSecurityPractice.repositories;

import org.springframework.data.repository.CrudRepository;

import springSecurityPractice.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
