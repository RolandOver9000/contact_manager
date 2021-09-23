package hu.futureofmedia.task.contactsapi.validator.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface ValidE164Format {
    String message() default "Not valid phone number for E-164";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
