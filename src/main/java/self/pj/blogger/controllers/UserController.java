package self.pj.blogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import self.pj.blogger.models.User;
import self.pj.blogger.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository uRepository)
    {
        this.userRepository = uRepository;
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('VIEW_SELF', 'VIEW_LIST')")
    public ResponseEntity<List<User>> getAllUsers()
    {

        return ResponseEntity.ok(userRepository.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id)
    {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user.get());
    }
}
