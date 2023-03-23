package self.pj.blogger.models;

public record LoginRequest(String username, String password) {
    public LoginRequest {
        username = username.toLowerCase();
    }
}
