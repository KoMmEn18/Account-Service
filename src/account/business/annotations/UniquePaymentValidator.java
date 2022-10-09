package account.business.annotations;

import account.business.PaymentService;
import account.business.User;
import account.business.UserService;
import account.business.dto.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniquePaymentValidator implements ConstraintValidator<UniquePayment, PaymentDTO> {

    private final PaymentService paymentService;
    private final UserService userService;

    @Autowired
    public UniquePaymentValidator(PaymentService paymentService, UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    public void initialize(UniquePayment constraintAnnotation) {}

    public boolean isValid(PaymentDTO payment, ConstraintValidatorContext context) {
        if (payment == null) {
            return true;
        }

        Optional<User> user = userService.findByEmail(payment.getEmployee());
        if (user.isEmpty()) {
            return true;
        }

        return paymentService.isUnique(user.get(), payment.getPeriod());
    }
}
