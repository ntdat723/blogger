package self.pj.blogger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import self.pj.blogger.models.Post;
import self.pj.blogger.models.User;
import self.pj.blogger.repositories.PostRepository;
import self.pj.blogger.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("users/{uid}/posts")
public class PostController {
    public static final int MAX_CONTENT_LENGTH = 255;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public PostController(UserRepository userRepository, PostRepository postRepository)
    {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    private boolean isInvalid(String content)
    {
        return content == null || content.trim().isEmpty() || content.length() > MAX_CONTENT_LENGTH;
    }

    @GetMapping("")
    public ResponseEntity<List<Post>> getAllPostBy(@PathVariable long uid)
    {
        Optional<User> user = userRepository.findById(uid);
        if (user.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(postRepository.findAllByUser(user.get()));
    }

    @GetMapping("/{pid}")
    public ResponseEntity<Post> getPost(@PathVariable long uid, @PathVariable long pid)
    {
        Optional<Post> post = postRepository.findByIdAndUserId(uid, pid);
        if (!userRepository.existsById(uid) || post.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(post.get());
    }

    @PostMapping("")
    public ResponseEntity<Post> createNewPost(@PathVariable long uid, @RequestBody String body)
    {
        if (isInvalid(body))
            return ResponseEntity.badRequest().build();

        Optional<User> user = userRepository.findById(uid);
        if (user.isEmpty())
            return ResponseEntity.notFound().build();
        Post post = new Post(body);
        post.setUser(user.get());
        postRepository.save(post);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{pid}")
    @Transactional
    public ResponseEntity<Post> deletePost(@PathVariable long uid, @PathVariable long pid)
    {
        if (!userRepository.existsById(uid)
                || !postRepository.existsByIdAndUserId(pid, uid))
            return ResponseEntity.notFound().build();
        postRepository.deleteById(pid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{pid}")
    @Transactional
    public ResponseEntity<Post> editPost(@PathVariable long uid,
                                         @PathVariable long pid,
                                         @RequestBody String content)
    {
        if (isInvalid(content))
            return ResponseEntity.badRequest().build();
        Optional<Post> post = postRepository.findByIdAndUserId(pid, uid);
        if (!userRepository.existsById(uid)
                || post.isEmpty())
            return ResponseEntity.notFound().build();
        Post found = post.get();
        found.setContent(content);
        postRepository.save(found);
        return ResponseEntity.ok(found);
    }
}
