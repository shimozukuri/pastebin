package project.shimozukuri.pastebin.exeptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AppError catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new AppError(e.getMessage());
    }

    @ExceptionHandler(
            {AccessDeniedException.class,
            org.springframework.security.access.AccessDeniedException.class}
    )
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public AppError catchAccessDeniedException(AccessDeniedException e) {
        log.error(e.getMessage(), e);
        return new AppError(e.getMessage());
    }

    @ExceptionHandler(MinioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError catchMinioException(MinioException e) {
        log.error(e.getMessage(), e);
        return new AppError(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError catchIllegalStateException(IllegalStateException e) {
        log.error(e.getMessage(), e);
        return new AppError(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e
    ) {
        log.error(e.getMessage(), e);
        AppError appError = new AppError("Validation failed.");
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        appError.setErrors(errors.stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existingMessage, newMessage) ->
                                existingMessage + " " + newMessage)
                ));
        return appError;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError handleConstraintViolation(
            final ConstraintViolationException e
    ) {
        log.error(e.getMessage(), e);
        AppError exceptionBody = new AppError("Validation failed.");
        exceptionBody.setErrors(e.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                )));
        return exceptionBody;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AppError catchException(Exception e) {
        log.error(e.getMessage(), e);
        return new AppError("Internal error");
    }
}
