package xyz.javista.web.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import xyz.javista.exception.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    ValidationErrorDTO handleValidationException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDTO error = new ValidationErrorDTO(BAD_REQUEST.value(), "VALIDATION_ERROR");
        for (org.springframework.validation.FieldError fieldError : fieldErrors) {
            error.addFieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        }
        return error;
    }

    @ExceptionHandler(UserRegistrationException.class)
    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    ErrorDTO handleRegistrationException(UserRegistrationException ex){
        return new ErrorDTO(HttpStatus.BAD_REQUEST.value(), ex.getFailReason().toString());
    }

    @ExceptionHandler(DateTimeConverterException.class)
    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    ErrorDTO handleDateTimeConverterException(DateTimeConverterException ex){
        return new ErrorDTO(HttpStatus.BAD_REQUEST.value(), ex.getFailReason().toString());
    }

    @ExceptionHandler(UserException.class)
    @ResponseBody
    ResponseEntity<ErrorDTO> handleUserException(UserException uex) {
        switch (uex.getFailReason()) {
            case USER_NOT_FOUND: return notFound(uex);
            default: return badRequest("General problem with User account.");
        }
    }

    @ExceptionHandler(OrderException.class)
    @ResponseBody
    ResponseEntity<ErrorDTO> handleOrderException(OrderException oex) {
        switch (oex.getFailReason()) {
            case NOT_ALLOWED: return badRequest(oex);
            case ORDER_NOT_EXIST: return notFound(oex);
            default: return badRequest("General problem with Orders.");
        }
    }

    @ExceptionHandler(OrderLineItemException.class)
    @ResponseBody
    ResponseEntity<ErrorDTO> handleOrderLineItemException(OrderLineItemException olex) {
        switch(olex.getFailReason()) {
            case NOT_ALLOWED:
            case ORDER_EXPIRED: return badRequest(olex);
            case ORDER_ITEM_NOT_EXIST: return notFound(olex);
            default: return badRequest("General problem with Order line item.");
        }
    }

    private ResponseEntity<ErrorDTO> notFound(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    private ResponseEntity<ErrorDTO> badRequest(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    private ResponseEntity<ErrorDTO> badRequest(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(HttpStatus.BAD_REQUEST.value(), message));
    }
}
