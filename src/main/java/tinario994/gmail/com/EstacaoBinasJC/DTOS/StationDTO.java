package tinario994.gmail.com.EstacaoBinasJC.DTOS;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import tinario994.gmail.com.EstacaoBinasJC.Models.Station;

@Getter
@Setter
public class StationDTO implements Serializable {

    private Integer id;
    private String name;
    private String location;
    private Set<BicycleDTO> bicycles = new HashSet<>();

    public StationDTO() {

    }

    public StationDTO(Station entityStation) {
        this.id = entityStation.getId();
        this.name = entityStation.getName();
        this.location = entityStation.getLocation();
    
        // Inclui bicicletas no DTO sem criar ciclos
        if (entityStation.getBicycles() != null) {
            entityStation.getBicycles().forEach(bicycle -> {
                this.bicycles.add(new BicycleDTO(bicycle, false)); // Evita incluir StationDTO dentro de BicycleDTO
            });
        }
    }
    

}
