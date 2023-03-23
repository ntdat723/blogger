package self.pj.blogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import self.pj.blogger.models.Post;
import self.pj.blogger.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUser(User user);
    boolean existsByIdAndUserId(long pid, long uid);
    Optional<Post> findByIdAndUserId(long pid, long uid);

    @Query("DELETE FROM Post p WHERE p.id=?1")
    @Modifying
    void deleteById(long pid);
}
