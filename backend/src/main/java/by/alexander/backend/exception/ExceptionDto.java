package by.alexander.backend.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

import static by.alexander.backend.util.DateUtil.EUROPE_MINSK_TIMEZONE;
import static by.alexander.backend.util.DateUtil.ISO_UTC_DATE_FORMAT;

@Data
@Builder
public class ExceptionDto {

    private final int status;
    private final String error;
    private final String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = ISO_UTC_DATE_FORMAT,
            timezone = EUROPE_MINSK_TIMEZONE)
    private final Instant timestamp;

    public static ExceptionDto of(HttpStatus status, String message) {
        return ExceptionDto.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .timestamp(Instant.now())
                .build();
    }

    public static ResponseEntity<ExceptionDto> buildResponse(HttpStatus status, String message) {
        return new ResponseEntity<>(ExceptionDto.of(status, message), status);
    }

}
