package project.shimozukuri.pastebin.dtos.authorization;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String username;
    private String password;
}
