package Spring_Security.Google_Oauth2.repository;

import Spring_Security.Google_Oauth2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    default User findByEmailOrThrow(String email) {
        return findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("User with email: " + email + " not found")
        );
    }
}
