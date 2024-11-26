package tinario994.gmail.com.EstacaoBinasJC.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tinario994.gmail.com.EstacaoBinasJC.Models.Rental;

@Repository
public interface RentalRepository  extends JpaRepository<Rental,Integer>{
    
}
