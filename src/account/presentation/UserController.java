package account.presentation;

import account.business.ChangeRole;
import account.business.User;
import account.business.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class UserController {

    private UserService userService;

    private final Set<String> AVAILABLE_ROLES = Set.of("USER", "ADMINISTRATOR", "ACCOUNTANT");

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public List<User> getUsers() {
        return userService.findAll();
    }

    @PutMapping("/user/role")
    public User changeUserRole(@Valid @RequestBody ChangeRole changeRole) {
        Optional<User> userOptional = userService.findByEmail(changeRole.getUser());
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }
        User user = userOptional.get();

        String operation = changeRole.getOperation();
        String role = changeRole.getRole();
        if (!AVAILABLE_ROLES.contains(role)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!");
        }

        if (operation.equals("GRANT")) {
            if (user.isAdmin() || role.equals("ADMINISTRATOR")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user cannot combine administrative and business roles!");
            }
            userService.grantRole(user, role);
        } else {
            if (!user.hasAuthority(role)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user does not have a role!");
            }
            if (role.equals("ADMINISTRATOR")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't remove ADMINISTRATOR role!");
            }
            if (user.getRoles().size() == 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user must have at least one role!");
            }
            userService.removeRole(user, role);
        }

        return user;
    }

    @DeleteMapping("/user/{email}")
    public Map<String, String> deleteUser(@PathVariable String email) {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }
        User user = userOptional.get();
        if (user.isAdmin()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't remove ADMINISTRATOR role!");
        }
        userService.delete(user);

        return Map.of("user", email, "status", "Deleted successfully!");
    }
}
