package tech.marcusvieira.flightinportugal.errorhandlers;

import static tech.marcusvieira.flightinportugal.constants.ErrorConstants.MESSAGE_REQUEST_PARAMETER_REQUIRED;
import static tech.marcusvieira.flightinportugal.constants.ErrorConstants.MESSAGE_THIRD_PARTY_PROVIDER_ERROR;
import static tech.marcusvieira.flightinportugal.constants.ErrorConstants.MESSAGE_UNKNOWN_ERROR;

import feign.FeignException;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.marcusvieira.flightinportugal.enums.ErrorData;
import tech.marcusvieira.flightinportugal.errorhandlers.exceptions.AirportNotFoundException;
import tech.marcusvieira.flightinportugal.errorhandlers.exceptions.FlightNotFoundException;
import tech.marcusvieira.flightinportugal.errorhandlers.exceptions.InvalidRequestParamsException;
import tech.marcusvieira.flightinportugal.utils.ErrorUtils;

@Slf4j
@ControllerAdvice
@RestController
public class CustomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleExceptions(Exception ex) {
        log.error("Internal server error: {} ", ex.getMessage(), ex);
        final ErrorItem errorItem = ErrorUtils.createError(ErrorData.UNKNOWN, MESSAGE_UNKNOWN_ERROR);
        return new ResponseEntity(createExceptionResponse(errorItem), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignExceptions(FeignException ex) {
        log.error("External API error: {} ", ex.getMessage(), ex);
        final ErrorItem errorItem = ErrorUtils
            .createError(ErrorData.THIRD_PARTY_PROVIDER_ERROR, MESSAGE_THIRD_PARTY_PROVIDER_ERROR);
        return new ResponseEntity(createExceptionResponse(errorItem), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidRequestParamsException.class)
    public final ResponseEntity<Object> handleInvalidItineraryExceptions(InvalidRequestParamsException ex) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getErrors());
        return new ResponseEntity(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ErrorItem errorItem = ErrorUtils
            .createError(ErrorData.REQUEST_PARAMETER_REQUIRED,
                String.format(MESSAGE_REQUEST_PARAMETER_REQUIRED, ex.getParameterName()));
        return new ResponseEntity(createExceptionResponse(errorItem), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(FlightNotFoundException.class)
    public final ResponseEntity<Object> handleFlightNotFoundExceptions(FlightNotFoundException ex) {
        final ErrorItem errorItem = ErrorUtils.createError(ErrorData.NOT_FOUND, ex.getMessage());
        return new ResponseEntity(createExceptionResponse(errorItem), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AirportNotFoundException.class)
    public final ResponseEntity<Object> handleAirportNotFoundExceptions(AirportNotFoundException ex) {
        final ErrorItem errorItem = ErrorUtils.createError(ErrorData.NOT_FOUND, ex.getMessage());
        return new ResponseEntity(createExceptionResponse(errorItem), HttpStatus.NOT_FOUND);
    }

    private ExceptionResponse createExceptionResponse(ErrorItem errorItem) {
        return new ExceptionResponse(Collections.singletonList(errorItem));
    }
}
