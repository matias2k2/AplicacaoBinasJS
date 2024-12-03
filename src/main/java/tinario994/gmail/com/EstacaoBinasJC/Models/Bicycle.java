package tinario994.gmail.com.EstacaoBinasJC.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonBackReference
    private Station currentStation;

    @OneToMany(mappedBy = "bicycle") // Certifique-se de usar "bicycle" como está na classe Rental
    private List<Rental> rentals = new ArrayList<>();

    public Bicycle() {
    }

    public Bicycle(Integer id, String marca, String color, String bleBeaconId, Station currentStation) {
        this.id = id;
        this.marca = marca;
        this.color = color;
        this.bleBeaconId = bleBeaconId;
        this.currentStation = currentStation;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Bicycle other = (Bicycle) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
