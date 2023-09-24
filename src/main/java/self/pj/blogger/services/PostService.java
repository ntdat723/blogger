package self.pj.blogger.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import self.pj.blogger.models.Post;
import self.pj.blogger.models.User;
import self.pj.blogger.repositories.PostRepository;
import self.pj.blogger.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    public static final int MAX_CONTENT_LENGTH = 255;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private boolean isInvalid(String content)
    {
        return content == null || content.trim().isEmpty() || content.length() > MAX_CONTENT_LENGTH;
    }

    public List<Post> getAllPostsByUid(Long uid)
    {
        Optional<User> user = userRepository.findById(uid);
        if (user.isEmpty())
            throw new RuntimeException("Not Found");
        return postRepository.findAllByUser(user.get());
    }

    public Post getPostBy(Long uid, Long pid)
    {
        Optional<Post> post = postRepository.findByIdAndUserId(pid, uid);
        if (!userRepository.existsById(uid) || post.isEmpty())
            throw new RuntimeException("Not Found");
        return post.get();
    }

    public Post createNewPost(Long uid, String body)
    {
        if (isInvalid(body))
            throw new RuntimeException();
        Optional<User> user = userRepository.findById(uid);
        if (user.isEmpty())
            throw new RuntimeException("Not found");
        Post post = new Post(body);
        post.setUser(user.get());
        postRepository.save(post);

        return post;
    }

    public void deletePost(Long uid, Long pid)
    {
        if (!userRepository.existsById(uid)
                || !postRepository.existsByIdAndUserId(pid, uid))
            throw new RuntimeException("Not found");
        postRepository.deleteById(pid);
    }

    public Post editPost(Long uid, Long pid, String content)
    {
        if (isInvalid(content))
            throw new RuntimeException("Invalid");
        Optional<Post> post = postRepository.findByIdAndUserId(pid, uid);
        if (!userRepository.existsById(uid)
                || post.isEmpty())
            throw new RuntimeException("Not found");
        Post found = post.get();
        found.setContent(content);
        postRepository.save(found);

        return found;
    }
}
