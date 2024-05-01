package project.shimozukuri.pastebin.dtos.note;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Note DTO")
public class NoteDto {
    @Schema(
            description = "Note title",
            example = "New note"
    )
    private String title;

    @Schema(
            description = "Note content",
            example = "Hello, it's new note"
    )
    private String content;
}
