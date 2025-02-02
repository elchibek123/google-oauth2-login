package Spring_Security.Google_Oauth2.controller;

import Spring_Security.Google_Oauth2.dto.response.LoginView;
import Spring_Security.Google_Oauth2.service.GoogleOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/google")
@RequiredArgsConstructor
public class GoogleOAuthController {
    private final GoogleOAuthService googleOAuthService;

    @GetMapping("/authorization-url")
    public ResponseEntity<String> getAuthorizationUrl() {
        String authUrl = googleOAuthService.generateAuthorizationUrl();
        return ResponseEntity.ok(authUrl);
    }

    @GetMapping("/callback")
    public ResponseEntity<LoginView> handleCallback(@RequestParam String code) {
        return ResponseEntity.ok(googleOAuthService.handleAuthorizationCode(code));
    }
}
