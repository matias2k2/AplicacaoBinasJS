package tinario994.gmail.com.EstacaoBinasJC.Models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "Locations")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double latitude;

    private double longitude;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    public Location() {}

    public Location(Integer id, double latitude, double longitude, Route route) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.route = route;
    }
}
