package account.business.dto;

import account.business.annotations.PaymentExist;
import account.business.annotations.UserExist;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PaymentExist
public class PaymentUpdateDTO {

    @NotBlank(message = "You have to specify the employee")
    @UserExist
    private String employee;

    @JsonFormat(pattern = "MM-yyyy")
    private YearMonth period;

    @Min(value = 0, message = "Salary must be non negative!")
    private Long salary;
}
