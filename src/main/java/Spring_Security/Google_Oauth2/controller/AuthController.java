package Spring_Security.Google_Oauth2.controller;

import Spring_Security.Google_Oauth2.dto.request.AuthRequest;
import Spring_Security.Google_Oauth2.dto.response.LoginView;
import Spring_Security.Google_Oauth2.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public LoginView login(@Valid @RequestBody AuthRequest request) {
        return authService.login(request);
    }
}
