package project.shimozukuri.pastebin.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.shimozukuri.pastebin.config.TestConfig;
import project.shimozukuri.pastebin.dtos.user.UserDto;
import project.shimozukuri.pastebin.entities.Role;
import project.shimozukuri.pastebin.entities.User;
import project.shimozukuri.pastebin.exeptions.ResourceNotFoundException;
import project.shimozukuri.pastebin.mappers.UserMapper;
import project.shimozukuri.pastebin.repositories.UserRepository;
import project.shimozukuri.pastebin.services.impls.RoleServiceImpl;
import project.shimozukuri.pastebin.services.impls.UserServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private RoleServiceImpl roleService;

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void UserServiceImpl_GetById_ReturnUser() {
        Long id = 1L;

        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User testUser = userService.getById(id);

        verify(userRepository).findById(id);

        assertEquals(user, testUser);
    }

    @Test
    public void UserServiceImpl_GetById_ReturnResourceNotFoundException() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> userService.getById(id)
        );

        verify(userRepository).findById(id);
    }

    @Test
    public void UserServiceImpl_GetByUsername_ReturnUser() {
        String username = "username";

        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User testUser = userService.getByUsername(username).get();

        verify(userRepository).findByUsername(username);

        assertEquals(user, testUser);
    }

    @Test
    public void UserServiceImpl_Update_ReturnUser() {
        Long id = 1L;
        String username = "username";
        String password = "password";

        User user = new User();
        user.setId(id);

        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword(password);
        userDto.setConfirmPassword(password);

        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User testUser = userService.update(id, userDto);

        verify(passwordEncoder).encode(password);
        verify(userRepository).findById(id);
        verify(userRepository).save(user);

        assertEquals(userDto.getUsername(), testUser.getUsername());
    }

    @Test
    public void UserServiceImpl_LoadUserByUsername_ReturnUserDetails() {
        String username = "username";

        User user = new User();
        user.setUsername(username);
        user.setPassword("password");
        user.setRoles(Collections.emptyList());

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails testUserDetails = userService.loadUserByUsername(username);

        verify(userRepository).findByUsername(username);

        assertEquals(userDetails, testUserDetails);
    }

    @Test
    public void UserServiceImpl_CreateNewUser_ReturnUser() {
        String username = "username";
        String password = "password";
        String encodedPassword = "encodedPassword";

        Role role = new Role();
        role.setName("USER_ROLE");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword(password);
        userDto.setConfirmPassword(password);

        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(roleService.getUserRole()).thenReturn(role);
        when(userRepository.save(user)).thenReturn(user);

        User testUser = userService.createNewUser(userDto);

        verify(userMapper).toEntity(userDto);
        verify(passwordEncoder).encode(password);
        verify(roleService).getUserRole();
        verify(userRepository).save(user);

        assertEquals(encodedPassword, testUser.getPassword());
        assertEquals(List.of(role), testUser.getRoles());
    }

    @Test
    public void UserServiceImpl_Delete_ReturnVoid() {
        Long id = 1L;

        userService.deleteById(id);

        verify(userRepository).deleteById(id);
    }

    @Test
    public void UserService_IsNoteOwner_ReturnTrue() {
        Long userId = 1L;
        Long noteId = 1L;

        when(userRepository.isNoteOwner(userId, noteId)).thenReturn(true);

        boolean isOwner = userService.isNoteOwner(userId, noteId);

        verify(userRepository).isNoteOwner(userId, noteId);

        assertTrue(isOwner);
    }

    @Test
    public void UserService_IsNoteOwner_ReturnFalse() {
        Long userId = 1L;
        Long noteId = 1L;

        when(userRepository.isNoteOwner(userId, noteId)).thenReturn(false);

        boolean isOwner = userService.isNoteOwner(userId, noteId);

        verify(userRepository).isNoteOwner(userId, noteId);

        assertFalse(isOwner);
    }
}
