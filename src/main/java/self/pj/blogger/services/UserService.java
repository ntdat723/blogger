package self.pj.blogger.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import self.pj.blogger.models.User;
import self.pj.blogger.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public User getUserById(Long id)
    {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new RuntimeException("Not found");
        return user.get();
    }
}
