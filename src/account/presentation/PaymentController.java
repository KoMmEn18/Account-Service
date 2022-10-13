package account.presentation;

import account.business.Payment;
import account.business.PaymentService;
import account.business.User;
import account.business.dto.PaymentDTO;
import account.business.dto.PaymentUpdateDTO;
import account.business.util.PaymentMapper;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Validated
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/empl/payment")
    public ResponseEntity<?> getEmployeePayments(@AuthenticationPrincipal User user, @RequestParam(required = false) String period) {
        List<PaymentDTO> payments;
        if (period == null) {
            payments = paymentService.findPaymentsByEmployee(user)
                    .stream()
                    .map(PaymentMapper::mapToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(payments);
        }

        PaymentDTO payment;
        try {
            YearMonth periodObject = YearMonth.parse(period, DateTimeFormatter.ofPattern("MM-yyyy"));
            payment = PaymentMapper.mapToDTO(paymentService.findPaymentByEmployeeAndPeriod(user, periodObject));
        } catch (DateTimeParseException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Period is not valid");
        }

        return ResponseEntity.ok(payment);
    }

    @PostMapping("/acct/payments")
    public Map<String, String> addPayments(@RequestBody @UniqueElements List<@Valid PaymentDTO> payments) {
        paymentService.saveAll(payments.stream()
                .map(PaymentMapper::mapToEntity)
                .collect(Collectors.toList()));

        return Map.of("status", "Added successfully!");
    }

    @PutMapping("/acct/payments")
    public Map<String, String> updatePayment(@RequestBody @Valid PaymentUpdateDTO paymentDTO) {
        Payment newPayment = PaymentMapper.mapToEntity(paymentDTO);
        Payment payment = paymentService.findPaymentByEmployeeAndPeriod(newPayment.getEmployee(), newPayment.getPeriod());
        paymentService.updateSalary(payment, newPayment.getSalary());

        return Map.of("status", "Updated successfully!");
    }
}
