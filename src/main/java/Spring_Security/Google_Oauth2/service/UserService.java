package Spring_Security.Google_Oauth2.service;

import Spring_Security.Google_Oauth2.entity.User;
import Spring_Security.Google_Oauth2.dto.google.GoogleUserInfo;
import Spring_Security.Google_Oauth2.dto.request.UserRequest;
import Spring_Security.Google_Oauth2.dto.response.SimpleResponseMessage;

public interface UserService {
    SimpleResponseMessage register(UserRequest request);

    User findOrCreateUser(GoogleUserInfo userInfo);
}
