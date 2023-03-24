package self.pj.blogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import self.pj.blogger.models.Post;
import self.pj.blogger.models.User;
import self.pj.blogger.repositories.PostRepository;
import self.pj.blogger.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/search")
public class SearchController {
    private UserRepository userRepository;
    private PostRepository postRepository;

    @Autowired
    public SearchController(UserRepository userRepository, PostRepository postRepository)
    {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Post>> searchPost(@RequestParam(required = false) String query,
                                                 @RequestParam(required = false) String user)
    {
        if (!StringUtils.hasText(query) && !StringUtils.hasText(user))
        {

        }
        if (!StringUtils.hasText(user))
        {
            System.out.println("no user");
            return ResponseEntity.ok(postRepository.findPostsByContent(query));
        }
        if (!StringUtils.hasText(query))
        {
            System.out.println("no query");
            Optional<User> searching = userRepository.findByUsername(user);
            if (searching.isEmpty())
                throw new UsernameNotFoundException("User with name " + user + "not found!");
            return ResponseEntity.ok(searching.get().getPosts());
        }

        return ResponseEntity.ok(postRepository.findPostsByContentAndUser(query, user));
    }
}
