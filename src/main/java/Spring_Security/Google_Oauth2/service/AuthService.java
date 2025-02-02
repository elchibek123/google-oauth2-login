package Spring_Security.Google_Oauth2.service;

import Spring_Security.Google_Oauth2.dto.request.AuthRequest;
import Spring_Security.Google_Oauth2.dto.response.LoginView;

public interface AuthService {
    LoginView login(AuthRequest request);
}
