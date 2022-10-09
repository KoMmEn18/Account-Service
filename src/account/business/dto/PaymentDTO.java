package account.business.dto;

import account.business.annotations.UniquePayment;
import account.business.annotations.UserExist;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.YearMonth;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@UniquePayment
public class PaymentDTO {

    @NotBlank(message = "You have to specify the employee")
    @UserExist
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String employee;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lastname;

    @JsonFormat(pattern = "MM-yyyy")
    private YearMonth period;

    @Min(value = 0, message = "Salary must be non negative!")
    private Long salary;

    @JsonGetter("period")
    public String getPeriodFormatted() {
        String monthName = period.getMonth().name().toLowerCase();
        monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1);
        return monthName + "-" + period.getYear();
    }

    @JsonGetter("salary")
    public String getSalaryFormatted() {
        return String.format("%d dollar(s) %d cent(s)", salary / 100, salary % 100);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDTO that = (PaymentDTO) o;
        return employee.equals(that.employee) && period.equals(that.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, period);
    }
}
