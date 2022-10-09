package account.business.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PaymentExistValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PaymentExist {
    String message() default "The payment does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

