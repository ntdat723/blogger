package self.pj.blogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import self.pj.blogger.models.Post;
import self.pj.blogger.models.User;

import java.util.List;
import java.util.Optional;

@Repository
@Component
public interface UserRepository extends JpaRepository<User, Long> {
    //Optional<User> findUserById(long id);
    Optional<User> findByUsername(String username);
}
