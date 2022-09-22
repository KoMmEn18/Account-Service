package account.presentation;

import account.business.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @GetMapping("/empl/payment")
    public User getEmployeePayments(@AuthenticationPrincipal User user) {
        return user;
    }

    @PostMapping("/acct/payments")
    public void postPayment() {

    }

    @PutMapping("/acct/payments")
    public void updatePayment() {

    }
}
