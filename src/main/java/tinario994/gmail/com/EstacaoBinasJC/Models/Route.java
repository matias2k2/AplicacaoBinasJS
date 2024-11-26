package tinario994.gmail.com.EstacaoBinasJC.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Routes")
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "bicycle_id", nullable = false)
    private Bicycle bicycle;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userRoute;

    @Column(nullable = false)
    private Date startTime;

    private Date endTime;

    private double distance;

    private int pointsEarned;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Location> trajectory = new ArrayList<>();

    public Route() {}

    public Route(Integer id, Bicycle bicycle, User userRoute, Date startTime, Date endTime, double distance, int pointsEarned) {
        this.id = id;
        this.bicycle = bicycle;
        this.userRoute = userRoute;
        this.startTime = startTime;
        this.endTime = endTime;
        this.distance = distance;
        this.pointsEarned = pointsEarned;
    }
}
