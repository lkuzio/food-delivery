package xyz.javista.mapper;

import org.springframework.stereotype.Component;
import xyz.javista.exception.DateTimeConverterException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class DateTimeConverter {

    LocalDateTime fromString(String datetime) throws DateTimeConverterException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            return LocalDateTime.parse(datetime, formatter);
        } catch (DateTimeParseException exception){
            throw new DateTimeConverterException(DateTimeConverterException.FailReason.INVALID_DATE_TIME_FORMAT);
        }
    }


}
