package self.pj.blogger.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import self.pj.blogger.config.filters.JwtFilter;
import self.pj.blogger.services.auth.TokenProvider;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenProvider tokenProvider;

    @Autowired
    public JwtConfigurer(TokenProvider provider)
    {
        this.tokenProvider = provider;
    }


    @Override
    public void configure(HttpSecurity builder) throws Exception {
        super.configure(builder);
        JwtFilter jwtFilter = new JwtFilter(tokenProvider);
        builder.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
