package ws.floodhazard.integration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ws.floodhazard.integration.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	 User getUserByEmail(String email);
}
