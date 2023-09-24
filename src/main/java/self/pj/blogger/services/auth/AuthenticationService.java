package self.pj.blogger.services.auth;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import self.pj.blogger.config.DatabaseUserDetails;
import self.pj.blogger.models.User;
import self.pj.blogger.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public AuthenticationService(UserRepository repository)
    {
        this.userRepository = repository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        System.out.println("checkpoint 5");
        if (user.isEmpty())
            throw new UsernameNotFoundException("USER NOT FOUND!");
        System.out.println("checkpoint 6");
//        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
//                user.get().getPassword(),
//                user.get().getRole().getAuthorities()
//                        .stream().map(a -> new SimpleGrantedAuthority(a.getAuthName())).collect(Collectors.toList()));
        User found = user.get();
        List<SimpleGrantedAuthority> grantedAuthorities = user.get()
                .getRole().getAuthorities()
                .stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthName()))
                .toList();
        return new DatabaseUserDetails(found.getId(), found.getUsername(), found.getPassword(), grantedAuthorities);
    }
}
