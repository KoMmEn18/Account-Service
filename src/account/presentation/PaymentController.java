package account.presentation;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @GetMapping("/empl/payment")
    public void getEmployeePayments() {

    }

    @PostMapping("/acct/payments")
    public void postPayment() {

    }

    @PutMapping("/acct/payments")
    public void updatePayment() {

    }
}
