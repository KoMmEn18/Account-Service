package account.business.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBreached {
    String message() default "The password is in the hacker's database!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
