package tinario994.gmail.com.EstacaoBinasJC.DTOS;

import lombok.Getter;
import lombok.Setter;
import tinario994.gmail.com.EstacaoBinasJC.Models.Location;

@Getter
@Setter
public class LocationDTO {
    private Integer id;

    private Double latitude;

    private Double longitude;

    private Integer routeId; // Novo campo para o ID da rota

    public LocationDTO() {
    }

    public LocationDTO(Location location) {
        this.id = location.getId();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.routeId = (location.getRoute() != null) ? location.getRoute().getId() : null;
    }

}
