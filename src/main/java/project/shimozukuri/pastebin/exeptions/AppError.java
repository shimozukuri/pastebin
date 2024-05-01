package project.shimozukuri.pastebin.exeptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class AppError {
    private String message;
    private Map<String, String> errors;

    public AppError(String message) {
        this.message = message;
    }
}
