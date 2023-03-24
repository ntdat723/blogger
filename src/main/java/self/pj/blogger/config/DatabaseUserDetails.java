package self.pj.blogger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import self.pj.blogger.models.Authority;
import self.pj.blogger.models.Role;
import self.pj.blogger.models.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DatabaseUserDetails implements UserDetails {
    private User user;
    private List<SimpleGrantedAuthority> authorities;
    //private long id;

    @Autowired
    public DatabaseUserDetails(User user)
    {
        this.user = user;
    }

    public DatabaseUserDetails(long id, String username, String password, List<SimpleGrantedAuthority> authorities)
    {
        user = new User(id, username, password);
        this.authorities = authorities;
    }

    public long getId()
    {
        return user.getId();
    }

    public String getRoleName()
    {
        return user.getRole().getName();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return user
//                .getRole()
//                .getAuthorities()
//                .stream().map((auth) -> new SimpleGrantedAuthority(auth.getAuthName()))
//                .collect(Collectors.toList());
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
