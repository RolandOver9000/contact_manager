package hu.futureofmedia.task.contactsapi.validator.annotation;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidE164Format, String> {

    @Override
    public void initialize(ValidE164Format constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        return phoneNumber == null || phoneNumber.equals("") || canBeParsedToE164(phoneNumber);
    }

    private boolean canBeParsedToE164(String phoneNumber) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            phoneUtil.parse(phoneNumber, "");
            return true;
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        return false;
    }
}
