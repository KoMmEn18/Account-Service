package account.business.annotations;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserExistValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UserExist {
    String message() default "The user does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    boolean reversed() default false;
}
