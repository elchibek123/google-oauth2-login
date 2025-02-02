package Spring_Security.Google_Oauth2.dto.request;

import Spring_Security.Google_Oauth2.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        String firstName,
        String lastName,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String password

) {
    public User toEntity() {
        User user = new User();
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);

        return user;
    }
}
