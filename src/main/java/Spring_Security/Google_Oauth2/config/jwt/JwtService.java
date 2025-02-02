package Spring_Security.Google_Oauth2.config.jwt;

import Spring_Security.Google_Oauth2.repository.UserRepository;
import com.auth0.jwt.JWT;
import Spring_Security.Google_Oauth2.entity.User;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class JwtService {
    @Value("${spring.security.jwt.secret_key}")
    private String secretKey;
    @Value("${spring.security.jwt.expiration}")
    private Long expiration;

    public static final String CLAIM_ID = "id";
    public static final String CLAIM_EMAIL = "email";
    public static final String CLAIM_ROLE = "role";

    private final UserRepository userRepository;

    public JwtToken createToken(User user) {
        ZonedDateTime issuedAt = ZonedDateTime.now();
        ZonedDateTime expiresAt = issuedAt.plusSeconds(expiration);

        String token = JWT.create()
                .withClaim(CLAIM_EMAIL, user.getEmail())
                .withClaim(CLAIM_ID, user.getId())
                .withClaim(CLAIM_ROLE, user.getRole().getAuthority())
                .withIssuedAt(issuedAt.toInstant())
                .withExpiresAt(expiresAt.toInstant())
                .sign(getAlgorithm());
        return new JwtToken(token, issuedAt, expiresAt);
    }

    public User verifyToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(getAlgorithm()).build();
        DecodedJWT verify = jwtVerifier.verify(token);
        String email = verify.getClaim(CLAIM_EMAIL).asString();
        return userRepository.findByEmailOrThrow(email);
    }

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }
}
