package tech.marcusvieira.flightinportugal.errorhandlers.exceptions;

import java.util.List;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.marcusvieira.flightinportugal.errorhandlers.ErrorItem;

@Getter
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidRequestParamsException extends RuntimeException {

    private List<ErrorItem> errors;

    public InvalidRequestParamsException(List<ErrorItem> errors) {
        this.errors = errors;
    }
}
