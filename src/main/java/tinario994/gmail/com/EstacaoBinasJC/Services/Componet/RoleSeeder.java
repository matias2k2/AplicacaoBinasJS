package tinario994.gmail.com.EstacaoBinasJC.Services.Componet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import tinario994.gmail.com.EstacaoBinasJC.Models.Role;
import tinario994.gmail.com.EstacaoBinasJC.Repository.RoleRepository;

@Component
public class RoleSeeder implements CommandLineRunner {
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByAuthority("ROLE_USER").isEmpty()) {
            Role roleUser = new Role();
            roleUser.setAuthority("ROLE_USER");
            roleRepository.save(roleUser);
        }
        
        if (roleRepository.findByAuthority("ROLE_ADMIN").isEmpty()) {
            Role roleAdmin = new Role();
            roleAdmin.setAuthority("ROLE_ADMIN");
            roleRepository.save(roleAdmin);
        }
    }
}