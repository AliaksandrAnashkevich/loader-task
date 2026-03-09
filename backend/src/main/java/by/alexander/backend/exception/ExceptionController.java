package by.alexander.backend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.util.stream.Collectors;

import static by.alexander.backend.util.ConstantsUtil.SEMICOLON;
import static by.alexander.backend.util.ConstantsUtil.SPACE;

@RestControllerAdvice
public class ExceptionController {

    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);
    private static final String ERROR = "Error... ";

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDto> handleException(AccessDeniedException e) {
        log.error(ERROR + e.getMessage());
        return ExceptionDto.buildResponse(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionDto> handleException(AuthenticationException e) {
        log.error(ERROR + e.getMessage());
        return ExceptionDto.buildResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleException(MethodArgumentNotValidException e) {
        log.error(ERROR + e.getMessage());
        String validationErrors = e.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + SPACE + fieldError.getDefaultMessage())
                .collect(Collectors.joining(SEMICOLON));
        return ExceptionDto.buildResponse(HttpStatus.BAD_REQUEST, validationErrors);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDto> handleException(RuntimeException e) {
        log.error(ERROR + e.getMessage());
        return ExceptionDto.buildResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleException(ResourceNotFoundException e) {
        log.error(ERROR + e.getMessage());
        return ExceptionDto.buildResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleException(Exception e) {
        log.error(ERROR + e.getMessage());
        return ExceptionDto.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
