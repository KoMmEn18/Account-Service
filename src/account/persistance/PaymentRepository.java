package account.persistance;

import account.business.Payment;
import account.business.User;
import org.springframework.data.repository.CrudRepository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

    boolean existsByEmployeeAndPeriod(User employee, YearMonth period);
    Optional<Payment> findPaymentByEmployeeAndPeriod(User employee, YearMonth period);
    List<Payment> findPaymentsByEmployeeOrderByPeriodDesc(User employee);
}
