package Spring_Security.Google_Oauth2.service.impl;

import Spring_Security.Google_Oauth2.config.jwt.JwtService;
import Spring_Security.Google_Oauth2.config.jwt.JwtToken;
import Spring_Security.Google_Oauth2.dto.request.AuthRequest;
import Spring_Security.Google_Oauth2.dto.response.LoginView;
import Spring_Security.Google_Oauth2.entity.User;
import Spring_Security.Google_Oauth2.repository.UserRepository;
import Spring_Security.Google_Oauth2.service.AuthService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public LoginView login(AuthRequest request) {
        User user = userRepository.findByEmailOrThrow(request.email());
        boolean matches = passwordEncoder.matches(request.password(), user.getPassword());
        if (!matches) throw new ValidationException("Password is incorrect!");
        JwtToken token = jwtService.createToken(user);
        return LoginView.fromUser(user, token);
    }
}
