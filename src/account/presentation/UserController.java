package account.presentation;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class UserController {

    @GetMapping("/user")
    public void getUser() {

    }

    @PutMapping("/user/role")
    public void changeUserRole() {

    }

    @DeleteMapping("/user")
    public void deleteUser() {

    }
}
