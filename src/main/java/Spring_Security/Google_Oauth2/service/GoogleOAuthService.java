package Spring_Security.Google_Oauth2.service;

import Spring_Security.Google_Oauth2.config.jwt.JwtService;
import Spring_Security.Google_Oauth2.config.jwt.JwtToken;
import Spring_Security.Google_Oauth2.entity.User;
import Spring_Security.Google_Oauth2.dto.google.GoogleTokenResponse;
import Spring_Security.Google_Oauth2.dto.google.GoogleUserInfo;
import Spring_Security.Google_Oauth2.dto.response.LoginView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class GoogleOAuthService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate;
    private final UserService userService;
    private final JwtService jwtService;

    public String generateAuthorizationUrl() {
        return UriComponentsBuilder
                .fromHttpUrl("https://accounts.google.com/o/oauth2/v2/auth")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", "email profile")
                .queryParam("access_type", "offline")
//                .queryParam("prompt", "consent")
                .build()
                .toUriString();
    }

    public LoginView handleAuthorizationCode(String code) {
        // Get access token
        GoogleTokenResponse tokenResponse = getAccessToken(code);

        // Get user info
        GoogleUserInfo userInfo = getUserInfo(tokenResponse.getAccessToken());

        // Find or create user
        User user = userService.findOrCreateUser(userInfo);

        // Generate JWT token
        JwtToken token = jwtService.createToken(user);

        return LoginView.fromUser(user, token);
    }

    private GoogleTokenResponse getAccessToken(String code) {
        HttpHeaders headers =new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", redirectUri);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        return restTemplate.postForObject(
                "https://oauth2.googleapis.com/token",
                request,
                GoogleTokenResponse.class
        );
    }

    private GoogleUserInfo getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v3/userinfo",
                HttpMethod.GET,
                entity,
                GoogleUserInfo.class
        ).getBody();
    }
}
