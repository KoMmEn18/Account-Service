package account.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessEventListener implements
        ApplicationListener<AuthenticationSuccessEvent> {

    private UserService userService;

    @Autowired
    public AuthenticationSuccessEventListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent e) {
        User user =  (User) e.getAuthentication().getPrincipal();
        if (user.getFailedAttempts() > 0) {
            userService.resetFailedAttempts(user);
        }
    }
}
