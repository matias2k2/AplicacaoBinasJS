package tinario994.gmail.com.EstacaoBinasJC.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@Table(name = "Bicycles")
public class Bicycle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bicycle_id")
    private Integer id;

    private String marca;

    private String color;

    @Column(unique = true, nullable = false)
    private String bleBeaconId;

    @ManyToOne
    @JoinColumn(name = "station_id")
    @JsonManagedReference
    private Station currentStation;

    @OneToMany(mappedBy = "bicycle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rental> rentals = new ArrayList<>();

    public Bicycle() {}

    public Bicycle(Integer id, String marca, String color, String bleBeaconId, Station currentStation) {
        this.id = id;
        this.marca = marca;
        this.color = color;
        this.bleBeaconId = bleBeaconId;
        this.currentStation = currentStation;
    }
}

