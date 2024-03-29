package project.shimozukuri.pastebin.services;

import org.springframework.http.ResponseEntity;
import project.shimozukuri.pastebin.dtos.authorization.JwtRequestDto;
import project.shimozukuri.pastebin.dtos.authorization.RegistrationUserDto;

public interface AuthService {

    ResponseEntity<?> createAuthToken(JwtRequestDto authRequest);

    ResponseEntity<?> createNewUser(RegistrationUserDto registrationUserDto);
}
