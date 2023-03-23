package self.pj.blogger.models;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "project.security")
public class CustomSecurity
{
    private List<String> publicUrls;
    private List<UrlPattern> protectedUrls;

    @Data
    public static class UrlPattern
    {
        private String urlPattern;
        private List<String> authorities;
    }
}
