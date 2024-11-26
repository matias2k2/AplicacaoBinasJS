package tinario994.gmail.com.EstacaoBinasJC.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tinario994.gmail.com.EstacaoBinasJC.Models.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

}
