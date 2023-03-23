package self.pj.blogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import self.pj.blogger.models.Authority;
import self.pj.blogger.models.Post;
import self.pj.blogger.models.Role;
import self.pj.blogger.models.User;
import self.pj.blogger.repositories.AuthorityRepository;
import self.pj.blogger.repositories.RoleRepository;
import self.pj.blogger.repositories.PostRepository;
import self.pj.blogger.repositories.UserRepository;

import java.util.Set;

@SpringBootApplication
public class BloggerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BloggerApplication.class, args);
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Authority a1 = new Authority("VIEW_SELF");
        Authority a2 = new Authority("VIEW_LIST");
        authorityRepository.save(a1);
        authorityRepository.save(a2);

        Role adm = new Role("ROLE_ADMIN");
        Role nor = new Role("ROLE_NORM");
        adm.getAuthorities().add(a1);
        adm.getAuthorities().add(a2);
        nor.getAuthorities().add(a1);
        roleRepository.save(adm);
        roleRepository.save(nor);
        User u1 = new User("a@gmail.com", "x", adm);
        User u2 = new User("b@hotmail.com", "x", nor);
        u1.setPassword(passwordEncoder.encode(u1.getPassword()));
        u2.setPassword(passwordEncoder.encode(u2.getPassword()));
        u1.addNewPost(new Post("1st post."));
        u2.addNewPost(new Post("Hello there!"));
        userRepository.save(u1);
        userRepository.save(u2);
    }
}
