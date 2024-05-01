package project.shimozukuri.pastebin.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "User DTO")
public class UserDto {

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

    @Schema(
            description = "Confirm password",
            example = "100"
    )
    private String confirmPassword;

    @Schema(
            description = "User email",
            example = "user@gmail.com"
    )
    private String email;
}
