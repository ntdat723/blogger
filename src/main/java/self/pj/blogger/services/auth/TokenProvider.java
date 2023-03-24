package self.pj.blogger.services.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import self.pj.blogger.config.DatabaseUserDetails;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TokenProvider
{
    @Value("${app.jwt.secret-key}")
    private String secretKey;
    private final String AUTHORITIES_KEY = "authorities";
    private final int EXPIRATION_TIME = 1;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

    public String createToken(Authentication authentication)
    {
        String authorities = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        LocalDateTime exp = LocalDateTime.now().plusHours(EXPIRATION_TIME);

        return Jwts.builder()
                .setId(String.valueOf(((DatabaseUserDetails) authentication.getPrincipal()).getId()))
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(Date.from(exp.toInstant(ZoneOffset.UTC)))
                .signWith(signatureAlgorithm, secretKey)
                .compact();
    }

    public Authentication getAuthenticationFromToken(String token)
    {
        if (!StringUtils.hasText(token))
        {
            return null;
        }
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            List<SimpleGrantedAuthority> authorities =
                    Arrays
                            .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                            .filter(auth -> !auth.trim().isEmpty())
                            .map(SimpleGrantedAuthority::new)
                            .toList();

            //User principal = new User(claims.getSubject(), "", authorities);
            UserDetails principal = new DatabaseUserDetails(Long.parseLong(claims.getId()), claims.getSubject(), "", authorities);
            return new UsernamePasswordAuthenticationToken(principal, token, authorities);
        } catch (Exception ex)
        {
            return null;
        }
    }
}
