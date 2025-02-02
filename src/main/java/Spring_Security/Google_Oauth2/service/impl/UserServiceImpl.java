package Spring_Security.Google_Oauth2.service.impl;

import Spring_Security.Google_Oauth2.dto.google.GoogleUserInfo;
import Spring_Security.Google_Oauth2.dto.request.UserRequest;
import Spring_Security.Google_Oauth2.dto.response.SimpleResponseMessage;
import Spring_Security.Google_Oauth2.entity.User;
import Spring_Security.Google_Oauth2.enums.Role;
import Spring_Security.Google_Oauth2.repository.UserRepository;
import Spring_Security.Google_Oauth2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public SimpleResponseMessage register(UserRequest request) {
        User user = request.toEntity();
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);

        userRepository.save(user);

        return new SimpleResponseMessage("User registered successfully!");
    }

    @Override
    public User findOrCreateUser(GoogleUserInfo userInfo) {
        return userRepository.findByEmail(userInfo.getEmail())
                .orElseGet(() -> createGoogleUser(userInfo));
    }

    private User createGoogleUser(GoogleUserInfo userInfo) {
        User user = User.builder()
                .email(userInfo.getEmail())
                .firstName(userInfo.getGivenName())
                .lastName(userInfo.getFamilyName())
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }
}
