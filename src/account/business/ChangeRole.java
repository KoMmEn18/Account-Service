package account.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRole {

    @NotBlank(message = "You have to specify the user")
    private String user;

    @NotBlank(message = "You have to specify the role")
    private String role;

    @Pattern(regexp="^(GRANT|REMOVE)$", message="Invalid operation")
    private String operation;
}
