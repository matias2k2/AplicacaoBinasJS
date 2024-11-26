package tinario994.gmail.com.EstacaoBinasJC.DTOS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private StationDTO currentStation;
    private List<RentalDTO> rentals = new ArrayList<>();

    public BicycleDTO() {

    }

    public BicycleDTO(Bicycle entitBicycle) {
        this.id = entitBicycle.getId();
        this.marca = entitBicycle.getMarca();
        this.color = entitBicycle.getColor();
        this.bleBeaconId = entitBicycle.getBleBeaconId();
        this.currentStation = new StationDTO(entitBicycle.getCurrentStation());
        entitBicycle.getRentals().forEach(x -> this.rentals.add(new RentalDTO(x)));
    }

    public BicycleDTO(Integer id, String marca, String color, String bleBeaconId, StationDTO currentStation,
            List<RentalDTO> rentals) {
        this.id = id;
        this.marca = marca;
        this.color = color;
        this.bleBeaconId = bleBeaconId;
        this.currentStation = currentStation;
        this.rentals = rentals;
    }

}
