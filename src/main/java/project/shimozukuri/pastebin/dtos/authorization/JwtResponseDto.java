package project.shimozukuri.pastebin.dtos.authorization;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Response after login")
public class JwtResponseDto {
    private String token;
}

