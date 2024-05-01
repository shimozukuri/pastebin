package project.shimozukuri.pastebin.exeptions;

public class MinioException extends RuntimeException{
    public MinioException(String message) {
        super(message);
    }
}
