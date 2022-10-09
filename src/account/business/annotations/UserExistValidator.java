package account.business.annotations;

import account.business.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UserExistValidator implements ConstraintValidator<UserExist, String> {

    private UserService userService;
    private boolean reversed;

    @Autowired
    public UserExistValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UserExist constraintAnnotation) {
        this.reversed = constraintAnnotation.reversed();
    }

    @Override
    public boolean isValid(String userEmail, ConstraintValidatorContext cxt) {
        return reversed != userService.exist(userEmail);
    }
}
