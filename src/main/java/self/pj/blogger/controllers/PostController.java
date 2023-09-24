package self.pj.blogger.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import self.pj.blogger.models.Post;
import self.pj.blogger.models.User;
import self.pj.blogger.repositories.PostRepository;
import self.pj.blogger.repositories.UserRepository;
import self.pj.blogger.services.PostService;
import self.pj.blogger.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/users/{uid}/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("")
    public ResponseEntity<List<Post>> getAllPostBy(@PathVariable long uid)
    {
        return ResponseEntity.ok(postService.getAllPostsByUid(uid));
    }

    @GetMapping("/{pid}")
    @PreAuthorize("principal.getId() == #uid")
    public ResponseEntity<Post> getPost(@PathVariable long uid, @PathVariable long pid)
    {
//        Authentication au = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(au.getPrincipal());
        return ResponseEntity.ok(postService.getPostBy(uid, pid));
    }

    @PostMapping("")
    public ResponseEntity<Post> createNewPost(@PathVariable long uid, @RequestBody String body)
    {
        return ResponseEntity.ok(postService.createNewPost(uid, body));
    }

    @DeleteMapping("/{pid}")
    @Transactional
    public ResponseEntity<Post> deletePost(@PathVariable long uid, @PathVariable long pid)
    {
        postService.deletePost(uid, pid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{pid}")
    @Transactional
    public ResponseEntity<Post> editPost(@PathVariable long uid,
                                         @PathVariable long pid,
                                         @RequestBody String content)
    {
        return ResponseEntity.ok(postService.editPost(uid, pid, content));
    }
}
