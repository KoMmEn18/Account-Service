package account.business;

import account.business.annotations.NotBreached;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPassword {

    @NotBlank
    @Size(min = 12, message = "The password length must be at least 12 chars!")
    @NotBreached()
    @JsonProperty("new_password")
    private String newPassword;
}
