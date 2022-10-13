package account.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationFailureListener implements
        ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final UserService userService;
    private final LogService logService;
    private final HttpServletRequest request;

    @Autowired
    public AuthenticationFailureListener(UserService userService, LogService logService, HttpServletRequest request) {
        this.userService = userService;
        this.logService = logService;
        this.request = request;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        String path = request.getRequestURI();
        String email = e.getAuthentication().getPrincipal().toString();
        logService.save(new Log(Event.LOGIN_FAILED, email, path, path));
        User user = userService.findByEmail(email).orElse(null);
        if (user != null) {
            if (user.getFailedAttempts() + 1 < UserService.MAX_FAILED_ATTEMPTS) {
                userService.increaseFailedAttempts(user);
            } else {
                logService.save(new Log(Event.BRUTE_FORCE, user.getEmail(), path, path));
                if (!user.isAdmin()) {
                    userService.updateLockStatus(user, true);
                }
                userService.resetFailedAttempts(user);
            }
        }
    }
}