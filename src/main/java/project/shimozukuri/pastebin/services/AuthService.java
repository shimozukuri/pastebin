package project.shimozukuri.pastebin.services;

import project.shimozukuri.pastebin.dtos.authorization.JwtRequestDto;
import project.shimozukuri.pastebin.dtos.authorization.JwtResponseDto;
import project.shimozukuri.pastebin.dtos.user.UserDto;
import project.shimozukuri.pastebin.entities.User;

public interface AuthService {

    JwtResponseDto createAuthToken(JwtRequestDto authRequest);

    User createNewUser(UserDto userDto);
}
