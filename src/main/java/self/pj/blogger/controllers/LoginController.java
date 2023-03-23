package self.pj.blogger.controllers;

import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import self.pj.blogger.models.JwtToken;
import self.pj.blogger.models.LoginRequest;
import self.pj.blogger.services.auth.LoginService;

@RestController
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService)
    {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> authorize(@Valid @RequestBody LoginRequest request)
    {
        System.out.println(request.username() + " " + request.password());
        String token = loginService.createToken(request.username(), request.password());
        System.out.println(token);
        return ResponseEntity.ok(JwtToken.builder().accessToken(token).build());
    }
}
