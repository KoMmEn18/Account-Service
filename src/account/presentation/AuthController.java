package account.presentation;

import account.business.User;
import account.business.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public User signup(@Validated @RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        /*
        if (!userService.save(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists in our system!");
        }
         */
        return user;
    }

    @PostMapping("/changepass")
    public void changePass() {
        /*
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (!userService.save(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists in our system!");
        }
         */
    }
}
