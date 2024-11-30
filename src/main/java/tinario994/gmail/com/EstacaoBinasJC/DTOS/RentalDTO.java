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

    public RentalDTO(Rental entity) {
        this.id = entity.getId();
        this.rentalStart = entity.getRentalStart();
        this.rentalEnd = entity.getRentalEnd();
        this.user = new UserDTO(entity.getUser());
       // if (entity.getBicycle() != null) {
       //     this.bicycle = new BicycleDTO(entity.getBicycle(), false);
        //}
    }

}
