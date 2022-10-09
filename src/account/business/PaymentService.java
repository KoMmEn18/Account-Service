package account.business;

import account.persistance.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment findPaymentByEmployeeAndPeriod(User employee, YearMonth period) {
        return paymentRepository.findPaymentByEmployeeAndPeriod(employee, period).orElse(null);
    }

    public List<Payment> findPaymentsByEmployee(User employee) {
        return paymentRepository.findPaymentsByEmployeeOrderByPeriodDesc(employee);
    }

    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    @Transactional
    public void saveAll(List<Payment> payments) {
        paymentRepository.saveAll(payments);
    }

    public boolean updateSalary(Payment payment, long salary) {
        if (payment != null) {
            payment.setSalary(salary);
            save(payment);

            return true;
        }

        return false;
    }

    public boolean isUnique(User employee, YearMonth period) {
        return !paymentRepository.existsByEmployeeAndPeriod(employee, period);
    }

    public boolean existByEmployeeIdAndPeriod(User employee, YearMonth period) {
        return paymentRepository.existsByEmployeeAndPeriod(employee, period);
    }
}
