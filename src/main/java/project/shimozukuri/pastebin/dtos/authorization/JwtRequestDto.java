package project.shimozukuri.pastebin.dtos.authorization;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request for login")
public class JwtRequestDto {

    @Schema(
            description = "Username",
            example = "user"
    )
    private String username;

    @Schema(
            description = "User password",
            example = "100"
    )
    private String password;
}
