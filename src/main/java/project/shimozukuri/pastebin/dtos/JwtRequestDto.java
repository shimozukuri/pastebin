package project.shimozukuri.pastebin.dtos;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String username;
    private String password;
}
