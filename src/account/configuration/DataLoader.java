package account.configuration;

import account.business.Role;
import account.persistance.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private RoleRepository roleRepository;

    @Autowired
    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        createRoles();
    }

    private void createRoles() {
        try {
            roleRepository.save(new Role("USER"));
            roleRepository.save(new Role("ADMINISTRATOR"));
            roleRepository.save(new Role("ACCOUNTANT"));
        } catch (Exception ignored) {}
    }
}
