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

import xyz.javista.exception.DateTimeConverterException;
import xyz.javista.exception.OrderException;
import xyz.javista.exception.UserException;
import xyz.javista.exception.UserRegistrationException;

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
        if (uex.getFailReason().equals(UserException.FailReason.USER_NOT_FOUND)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(HttpStatus.NOT_FOUND.value(), uex.getMessage()));
        }
        return ResponseEntity.badRequest().body(new ErrorDTO(HttpStatus.BAD_REQUEST.value(), "General problem with User account."));
    }

    @ExceptionHandler(OrderException.class)
    @ResponseBody
    ResponseEntity<ErrorDTO> handleOrderException(OrderException oex) {
        if (oex.getFailReason().equals(OrderException.FailReason.ORDER_NOT_EXIST)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(HttpStatus.NOT_FOUND.value(), oex.getMessage()));
        }
        return ResponseEntity.badRequest().body(new ErrorDTO(HttpStatus.BAD_REQUEST.value(), "General problem with Orders."));
    }

}
