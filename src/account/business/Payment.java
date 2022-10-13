package account.business;

import account.business.util.YearMonthDateAttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private User employee;

    @NotNull
    @Convert(converter = YearMonthDateAttributeConverter.class)
    private YearMonth period;

    @Min(value = 0, message = "Salary must be non negative!")
    private Long salary;

    public Payment(User employee, YearMonth period, Long salary) {
        this.employee = employee;
        this.period = period;
        this.salary = salary;
    }
}
