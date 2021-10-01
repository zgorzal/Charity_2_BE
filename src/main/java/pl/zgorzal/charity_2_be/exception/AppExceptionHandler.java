package pl.zgorzal.charity_2_be.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {AppRequestException.class})
    public ResponseEntity<Object> handleAppRequestException(AppRequestException e) {
        AppException appException = new AppException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Europe/Warsaw"))
        );
        return new ResponseEntity<>(appException, HttpStatus.NOT_FOUND);
    }

}
