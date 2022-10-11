package account.business;

import account.persistance.RoleRepository;
import account.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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

        return true;
    }

    public void grantRole(User user, String roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        role.ifPresent(user::grantAuthority);
        userRepository.save(user);
    }

    public void removeRole(User user, String roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        role.ifPresent(user::removeAuthority);
        userRepository.save(user);
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
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
