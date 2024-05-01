package project.shimozukuri.pastebin.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.shimozukuri.pastebin.config.TestConfig;
import project.shimozukuri.pastebin.dtos.authorization.JwtRequestDto;
import project.shimozukuri.pastebin.dtos.authorization.JwtResponseDto;
import project.shimozukuri.pastebin.dtos.user.UserDto;
import project.shimozukuri.pastebin.entities.User;
import project.shimozukuri.pastebin.exeptions.AccessDeniedException;
import project.shimozukuri.pastebin.services.impls.AuthServiceImpl;
import project.shimozukuri.pastebin.services.impls.UserServiceImpl;
import project.shimozukuri.pastebin.utils.JwtTokenUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTests {

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthServiceImpl authService;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Test
    public void AuthServiceImpl_CreateAuthToken_ReturnJwtResponseDto() {
        List<SimpleGrantedAuthority> roleList = Collections.emptyList();
        String username = "username";
        String password = "password";
        String token = "token";

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                username,
                password,
                roleList
        );
        JwtResponseDto testResponse = new JwtResponseDto(token);
        JwtRequestDto request = new JwtRequestDto(username, password);

        when(userService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn(token);

        JwtResponseDto response = authService.createAuthToken(request);

        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        verify(userService).loadUserByUsername(username);
        verify(jwtTokenUtil).generateToken(userDetails);

        assertEquals(response.getToken(), testResponse.getToken());
    }

    @Test
    public void AuthServiceImpl_CreateNewUser_ReturnUser() {
        String username = "username";
        String password = "password";

        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword(password);
        userDto.setConfirmPassword(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        when(userService.getByUsername(username)).thenReturn(Optional.empty());
        when(userService.createNewUser(userDto)).thenReturn(user);

        User testUser = authService.createNewUser(userDto);

        verify(userService).getByUsername(username);
        verify(userService).createNewUser(userDto);

        assertEquals(user, testUser);
    }

    @Test
    public void AuthServiceImpl_CreateNewUser_ReturnAccessDeniedException() {
        String password = "password";
        String confirmPassword = "password1";

        UserDto userDto = new UserDto();
        userDto.setPassword(password);
        userDto.setConfirmPassword(confirmPassword);

        assertThrows(
                AccessDeniedException.class,
                () -> authService.createNewUser(userDto)
        );
    }

    @Test
    public void AuthServiceImpl_CreateNewUser_ReturnIllegalStateException() {
        String username = "username";
        String password = "password";

        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword(password);
        userDto.setConfirmPassword(password);

        when(userService.getByUsername(username)).thenReturn(Optional.of(new User()));

        assertThrows(
                IllegalStateException.class,
                () -> authService.createNewUser(userDto)
        );
    }
}
