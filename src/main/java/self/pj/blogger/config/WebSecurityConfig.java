package self.pj.blogger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import self.pj.blogger.models.CustomSecurity;
import self.pj.blogger.services.auth.AuthenticationService;
import self.pj.blogger.services.auth.TokenProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final CustomSecurity customSecurity;
    private final CorsFilter corsFilter;
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public WebSecurityConfig(CustomSecurity customSecurity, TokenProvider provider, CorsFilter corsFilter,
                             JwtAuthenticationEntryPoint entryPoint)
    {
        this.customSecurity = customSecurity;
        this.tokenProvider = provider;
        this.jwtAuthenticationEntryPoint = entryPoint;
        this.corsFilter = corsFilter;
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        for (String url : customSecurity.getPublicUrls())
        {
            http.authorizeHttpRequests().requestMatchers(url).permitAll();
        }
        for (CustomSecurity.UrlPattern up : customSecurity.getProtectedUrls())
        {
            for (String authority : up.getAuthorities())
            {
                http.authorizeHttpRequests().requestMatchers(up.getUrlPattern()).hasAuthority(authority);
            }
        }

        http
                .authorizeHttpRequests()
                .anyRequest().authenticated()
        .and()
                .httpBasic()
        .and()
                .apply(jwtConfigurer());

        return http.build();
    }

    private JwtConfigurer jwtConfigurer()
    {
        return new JwtConfigurer(tokenProvider);
    }

//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider()
//    {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService());
//        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
//
//        return authenticationProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder builder) throws Exception
//    {
//        return builder.authenticationProvider(daoAuthenticationProvider()).getOrBuild();
//    }
//    @Bean
//    public UserDetailsService userDetailsService()
//    {
//        return new AuthenticationService();
//    }
}
