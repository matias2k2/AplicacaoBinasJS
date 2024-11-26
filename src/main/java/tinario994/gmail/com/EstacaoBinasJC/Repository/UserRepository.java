package tinario994.gmail.com.EstacaoBinasJC.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tinario994.gmail.com.EstacaoBinasJC.Models.User;

@Repository
public interface UserRepository extends  JpaRepository<User, Integer>{
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
