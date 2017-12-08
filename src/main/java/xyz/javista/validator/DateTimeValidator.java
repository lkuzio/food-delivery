package xyz.javista.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeValidator implements ConstraintValidator<ValidDateTimeFormat, String> {
    public void initialize(ValidDateTimeFormat constraint) {
    }

    public boolean isValid(String obj, ConstraintValidatorContext context) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            LocalDateTime.parse(obj, formatter);
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        }
    }
}
