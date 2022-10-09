package account.business.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniquePaymentValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePayment {
    String message() default "Pair employee-period is not unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
