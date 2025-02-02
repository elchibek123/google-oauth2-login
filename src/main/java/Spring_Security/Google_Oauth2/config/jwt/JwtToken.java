package Spring_Security.Google_Oauth2.config.jwt;

import java.time.ZonedDateTime;

public record JwtToken(
        String accessToken,
        ZonedDateTime issuedAt,
        ZonedDateTime expiresAt
) {
}
