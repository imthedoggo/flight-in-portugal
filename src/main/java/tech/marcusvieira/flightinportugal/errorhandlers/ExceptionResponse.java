package tech.marcusvieira.flightinportugal.errorhandlers;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {

    private List<ErrorItem> errors;
}
