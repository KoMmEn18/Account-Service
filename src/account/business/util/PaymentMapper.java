package account.business.util;

import account.business.Payment;
import account.business.UserService;
import account.business.dto.PaymentDTO;
import account.business.dto.PaymentUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    private static UserService userService;

    @Autowired
    public PaymentMapper(UserService userService) {
        PaymentMapper.userService = userService;
    }

    public static Payment mapToEntity(PaymentDTO paymentDTO) {
        return new Payment(
                userService.findByEmail(paymentDTO.getEmployee()).get(),
                paymentDTO.getPeriod(),
                paymentDTO.getSalary()
        );
    }

    public static Payment mapToEntity(PaymentUpdateDTO paymentDTO) {
        return new Payment(
                userService.findByEmail(paymentDTO.getEmployee()).get(),
                paymentDTO.getPeriod(),
                paymentDTO.getSalary()
        );
    }

    public static PaymentDTO mapToDTO(Payment payment) {
        return new PaymentDTO(
                payment.getEmployee().getEmail(),
                payment.getEmployee().getName(),
                payment.getEmployee().getLastname(),
                payment.getPeriod(),
                payment.getSalary()
        );
    }
}
