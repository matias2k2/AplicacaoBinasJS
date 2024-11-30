package tinario994.gmail.com.EstacaoBinasJC.DTOS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import tinario994.gmail.com.EstacaoBinasJC.Models.Bicycle;

@Getter
@Setter
public class BicycleDTO implements Serializable {

    private Integer id;
    private String marca;
    private String color;
    private String bleBeaconId;

    // Para evitar serialização recursiva
    @JsonIgnore
    private StationDTO currentStation;

    // Construtor padrão
    public BicycleDTO() {}

    // Construtor completo
    public BicycleDTO(Integer id, String marca, String color, String bleBeaconId, StationDTO currentStation) {
        this.id = id;
        this.marca = marca;
        this.color = color;
        this.bleBeaconId = bleBeaconId;
        this.currentStation = currentStation;
    }

    // Construtor com controle de associação recursiva
    public BicycleDTO(Bicycle entityBicycle, boolean includeStation) {
        this.id = entityBicycle.getId();
        this.marca = entityBicycle.getMarca();
        this.color = entityBicycle.getColor();
        this.bleBeaconId = entityBicycle.getBleBeaconId();
    
        if (includeStation && entityBicycle.getCurrentStation() != null) {
            this.currentStation = new StationDTO(entityBicycle.getCurrentStation());
        }
    }
    public BicycleDTO(Bicycle entityBicycle) {
        this(entityBicycle, true); // Por padrão, inclui o StationDTO
    }
    
    
}

