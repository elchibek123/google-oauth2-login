package Spring_Security.Google_Oauth2.dto.response;

import Spring_Security.Google_Oauth2.config.jwt.JwtToken;
import Spring_Security.Google_Oauth2.entity.User;
import Spring_Security.Google_Oauth2.enums.Role;
import lombok.Builder;

@Builder
public record LoginView(
        Long id,
        String firstName,
        String lastName,
        String email,
        Role role,
        JwtToken accessToken
) {
    public static LoginView fromUser(User user, JwtToken token) {
        return new LoginView(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                token
        );
    }
}
