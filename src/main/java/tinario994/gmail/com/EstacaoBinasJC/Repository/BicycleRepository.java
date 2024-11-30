package tinario994.gmail.com.EstacaoBinasJC.Repository;

import java.util.List;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tinario994.gmail.com.EstacaoBinasJC.Models.Bicycle;

@Repository
public interface BicycleRepository extends JpaRepository<Bicycle, Integer> {
     // Optional<Station> findByName(String name);

     @Query("SELECT b FROM Bicycle b JOIN FETCH b.currentStation")
     List<Bicycle> findByIdWithStation();

     @Query("SELECT b FROM Bicycle b JOIN FETCH b.currentStation s WHERE b.currentStation IS NOT NULL")
     Page<Bicycle> findAllWithStations(PageRequest pageRequest);

}
