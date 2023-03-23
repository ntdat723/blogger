package self.pj.blogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import self.pj.blogger.models.Authority;
import self.pj.blogger.models.Role;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT a FROM Role r JOIN Authority a WHERE r.id = ?1")
    List<Authority> findAllAssociatedAuthorities(Long id);
}
