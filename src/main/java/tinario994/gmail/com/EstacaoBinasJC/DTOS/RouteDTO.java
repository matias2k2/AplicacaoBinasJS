package tinario994.gmail.com.EstacaoBinasJC.DTOS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import tinario994.gmail.com.EstacaoBinasJC.Models.Route;


@Getter
@Setter
public class RouteDTO {

    private Integer id;

    private BicycleDTO bicycle;

    private UserDTO userRoute;

    private Date startTime;

    private Date endTime;

    private double distance;

    private int pointsEarned;

    private List<LocationDTO> trajectory = new ArrayList<>();

    public RouteDTO(){}

    public RouteDTO(Route entityRoute) {
        this.id = entityRoute.getId();
        this.bicycle = new BicycleDTO(entityRoute.getBicycle());
        this.userRoute = new UserDTO(entityRoute.getUserRoute());
        this.startTime = entityRoute.getStartTime();
        this.endTime = entityRoute.getEndTime();
        this.distance = entityRoute.getDistance();
        this.pointsEarned = entityRoute.getPointsEarned();
        entityRoute.getTrajectory().forEach(x -> this.trajectory.add(new LocationDTO(x)));
    }



    public RouteDTO(Integer id, BicycleDTO bicycle, UserDTO userRoute, Date startTime, Date endTime, double distance,
            int pointsEarned, List<LocationDTO> trajectory) {
        this.id = id;
        this.bicycle = bicycle;
        this.userRoute = userRoute;
        this.startTime = startTime;
        this.endTime = endTime;
        this.distance = distance;
        this.pointsEarned = pointsEarned;
        this.trajectory = trajectory;
    }

}
