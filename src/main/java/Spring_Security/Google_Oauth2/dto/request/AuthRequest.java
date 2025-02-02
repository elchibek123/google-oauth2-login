package Spring_Security.Google_Oauth2.dto.request;

import jakarta.validation.constraints.NotNull;

public record AuthRequest(
        @NotNull
        String email,

        @NotNull
        String password
) {
}
