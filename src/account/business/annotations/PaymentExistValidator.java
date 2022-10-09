package account.business.annotations;

import account.business.PaymentService;
import account.business.User;
import account.business.UserService;
import account.business.dto.PaymentUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class PaymentExistValidator implements ConstraintValidator<PaymentExist, PaymentUpdateDTO> {

    private final PaymentService paymentService;
    private final UserService userService;

    @Autowired
    public PaymentExistValidator(PaymentService paymentService, UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    public void initialize(PaymentExist constraintAnnotation) {}

    public boolean isValid(PaymentUpdateDTO payment, ConstraintValidatorContext context) {
        if (payment == null) {
            return false;
        }

        Optional<User> user = userService.findByEmail(payment.getEmployee());
        if (user.isEmpty()) {
            return false;
        }

        return paymentService.existByEmployeeIdAndPeriod(user.get(), payment.getPeriod());
    }
}