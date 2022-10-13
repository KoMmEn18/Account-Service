package account.presentation;

import account.business.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public User signup(@Valid @RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (!userService.save(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        }

        return user;
    }

    @PostMapping("/changepass")
    public Map<String, String> changePass(@Valid @RequestBody NewPassword password, @AuthenticationPrincipal User user) {
        if (passwordEncoder.matches(password.getNewPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The passwords must be different!");
        }
        userService.updatePassword(user, passwordEncoder.encode(password.getNewPassword()));

        return Map.of("email", user.getEmail(), "status", "The password has been updated successfully");
    }
}
