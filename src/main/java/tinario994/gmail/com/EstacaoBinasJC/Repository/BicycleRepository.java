package tinario994.gmail.com.EstacaoBinasJC.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tinario994.gmail.com.EstacaoBinasJC.Models.Bicycle;


@Repository
public interface BicycleRepository extends  JpaRepository<Bicycle, Integer> {
     //Optional<Station> findByName(String name);
}
