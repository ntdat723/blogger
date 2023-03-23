package self.pj.blogger.models;

import lombok.Builder;

@Builder
public record JwtToken(String accessToken) {
}
