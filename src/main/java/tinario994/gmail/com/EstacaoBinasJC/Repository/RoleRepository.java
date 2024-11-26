package tinario994.gmail.com.EstacaoBinasJC.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tinario994.gmail.com.EstacaoBinasJC.Models.Role;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
     Optional<Role> findByAuthority(String authority);
}
