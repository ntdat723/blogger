package self.pj.blogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import self.pj.blogger.models.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
