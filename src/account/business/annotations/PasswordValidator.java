package account.business.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

public class PasswordValidator implements ConstraintValidator<NotBreached, String> {

    private final Set<String> breachedPasswords = Set.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch",
            "PasswordForApril", "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");

    @Override
    public void initialize(NotBreached constraintAnnotation) {}

    @Override
    public boolean isValid(String passwordField, ConstraintValidatorContext cxt) {
        return passwordField == null || !breachedPasswords.contains(passwordField);
    }
}
