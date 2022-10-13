package account.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeLockStatus {

    @NotBlank(message = "You have to specify the user")
    private String user;

    @Pattern(regexp="^(LOCK|UNLOCK)$", message="Invalid operation")
    private String operation;
}
