package xyz.javista.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateTimeValidator.class)
public @interface ValidDateTimeFormat {

    String message() default "DateTime should be in format : \"yyyy-MM-dd HH:mm\"";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
