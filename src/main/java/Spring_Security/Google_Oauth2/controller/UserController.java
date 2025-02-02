package Spring_Security.Google_Oauth2.controller;

import Spring_Security.Google_Oauth2.dto.request.UserRequest;
import Spring_Security.Google_Oauth2.dto.response.SimpleResponseMessage;
import Spring_Security.Google_Oauth2.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public SimpleResponseMessage register(@Valid @RequestBody UserRequest request) {
        return userService.register(request);
    }
}
