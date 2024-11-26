package tinario994.gmail.com.EstacaoBinasJC.Models;

import java.io.Serializable;

import jakarta.persistence.Column;
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
@Table(name = "Rentals")
public class Rental implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String rentalStart;

    @Column(nullable = true)
    private String rentalEnd;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "bicycle_id", nullable = false)
    private Bicycle bicycle;

    public Rental() {}

    public Rental(Integer id, String rentalStart, String rentalEnd, User user, Bicycle bicycle) {
        this.id = id;
        this.rentalStart = rentalStart;
        this.rentalEnd = rentalEnd;
        this.user = user;
        this.bicycle = bicycle;
    }
}
