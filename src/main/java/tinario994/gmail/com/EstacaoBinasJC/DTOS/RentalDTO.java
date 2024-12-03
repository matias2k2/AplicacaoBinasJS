package tinario994.gmail.com.EstacaoBinasJC.DTOS;

import lombok.Getter;
import lombok.Setter;
import tinario994.gmail.com.EstacaoBinasJC.Models.Rental;

@Getter
@Setter
public class RentalDTO {

    private Integer id;
    private String rentalStart;
    private String rentalEnd;
    private UserDTO user;
    private BicycleDTO bicycle;

    public RentalDTO() {
    }

    public RentalDTO(Integer id, String rentalStart, String rentalEnd, UserDTO user, BicycleDTO bicycle) {
        this.id = id;
        this.rentalStart = rentalStart;
        this.rentalEnd = rentalEnd;
        this.user = user;
        this.bicycle = bicycle;
    }

    public RentalDTO(Rental entity, boolean includeBicycle) {
        this.id = entity.getId();
        this.rentalStart = entity.getRentalStart();
        this.rentalEnd = entity.getRentalEnd();
        this.user = new UserDTO(entity.getUser());

        if (includeBicycle && entity.getBicycle() != null) {
            this.bicycle = new BicycleDTO(entity.getBicycle(), false);
        }
    }

    // Construtor padrão de conversão
    public RentalDTO(Rental entity) {
        this(entity, true);
    }

}
