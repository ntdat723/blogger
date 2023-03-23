package self.pj.blogger.config.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import self.pj.blogger.services.auth.TokenProvider;

import java.io.IOException;
import java.util.Objects;

public class JwtFilter extends GenericFilterBean {
    private static final String HEADER = "Authorization";
    private final TokenProvider tokenProvider;

    @Autowired
    public JwtFilter(TokenProvider provider)
    {
        this.tokenProvider = provider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        String token = resolveToken(servletRequest);
        Authentication authentication = tokenProvider.getAuthenticationFromToken(token);
        if (Objects.nonNull(authentication))
        {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(ServletRequest request)
    {
        String fromHeader = ((HttpServletRequest) request).getHeader(HEADER);
        if (StringUtils.hasText(fromHeader) && fromHeader.startsWith("Bearer"))
            return fromHeader.substring(7);
        return null;
    }
}
