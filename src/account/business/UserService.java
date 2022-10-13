package account.business;

import account.persistance.RoleRepository;
import account.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    public static final int MAX_FAILED_ATTEMPTS = 5;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final LogService logService;
    private final HttpServletRequest request;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, LogService logService, HttpServletRequest request) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.logService = logService;
        this.request = request;
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderById();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    public boolean exist(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    public boolean save(User user) {
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            return false;
        }

        Role role = userRepository.count() > 0 ? roleRepository.findByName("USER").get() : roleRepository.findByName("ADMINISTRATOR").get();
        user.grantAuthority(role);
        user.setEmail(user.getEmail().toLowerCase());
        userRepository.save(user);
        logService.save(new Log(Event.CREATE_USER, null, user.getEmail(), request.getRequestURI()));

        return true;
    }

    public void grantRole(User user, String roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        role.ifPresentOrElse(user::grantAuthority, () -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!");
        });
        userRepository.save(user);
        logService.save(new Log(
                Event.GRANT_ROLE,
                request.getUserPrincipal().getName(),
                "Grant role " + roleName + " to " + user.getEmail(),
                request.getRequestURI()
        ));
    }

    public void removeRole(User user, String roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        role.ifPresentOrElse(user::removeAuthority, () -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!");
        });
        userRepository.save(user);
        logService.save(new Log(
                Event.REMOVE_ROLE,
                request.getUserPrincipal().getName(),
                "Remove role " + roleName + " from " + user.getEmail(),
                request.getRequestURI()
        ));
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        userRepository.save(user);
        logService.save(new Log(Event.CHANGE_PASSWORD, user.getEmail(), user.getEmail(), request.getRequestURI()));
    }

    public void updateLockStatus(User user, boolean locked) {
        user.setLocked(locked);
        userRepository.save(user);
        logService.save(new Log(
                locked ? Event.LOCK_USER : Event.UNLOCK_USER,
                request.getUserPrincipal() != null ?  request.getUserPrincipal().getName() : user.getEmail(),
                (locked ? "Lock" : "Unlock") + " user " + user.getEmail(),
                request.getRequestURI()
        ));
    }

    public void increaseFailedAttempts(User user) {
        int newFailedAttempts = user.getFailedAttempts() + 1;
        user.setFailedAttempts(newFailedAttempts);
        userRepository.save(user);
    }

    public void resetFailedAttempts(User user) {
        user.setFailedAttempts(0);
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
        logService.save(new Log(
                Event.DELETE_USER,
                request.getUserPrincipal().getName(),
                user.getEmail(),
                request.getRequestURI()
        ));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findByEmailIgnoreCase(email);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Not found user of given email: " + email);
        }

        return user.get();
    }
}
