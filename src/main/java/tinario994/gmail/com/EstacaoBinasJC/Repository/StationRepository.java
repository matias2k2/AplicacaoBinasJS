package tinario994.gmail.com.EstacaoBinasJC.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tinario994.gmail.com.EstacaoBinasJC.Models.Station;


@Repository
public interface StationRepository extends JpaRepository<Station, Integer> {
    @Query("SELECT s FROM Station s WHERE s.name = :name")
    Optional<Station> findByName(@Param("name") String name); 
}
