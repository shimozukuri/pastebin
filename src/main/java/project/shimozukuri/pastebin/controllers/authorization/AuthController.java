package project.shimozukuri.pastebin.controllers.authorization;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shimozukuri.pastebin.dtos.authorization.JwtRequestDto;
import project.shimozukuri.pastebin.dtos.authorization.JwtResponseDto;
import project.shimozukuri.pastebin.dtos.user.UserDto;
import project.shimozukuri.pastebin.entities.User;
import project.shimozukuri.pastebin.services.AuthService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Auth controller", description = "Auth API")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public JwtResponseDto createAuthToken(@RequestBody JwtRequestDto authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/register")
    public User createNewUser(@RequestBody UserDto userDto) {
        return authService.createNewUser(userDto);
    }
}
