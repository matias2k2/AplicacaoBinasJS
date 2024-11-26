package tinario994.gmail.com.EstacaoBinasJC.Models;

import java.io.Serializable;

import java.util.HashSet;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Stations")
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private Integer id;

    private String name;

    private String location;

    @OneToMany(mappedBy = "currentStation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Bicycle> bicycles = new HashSet<>();

    public Station() {}

    public Station(Integer id, String location, String name) {
        this.id = id;
        this.location = location;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Station [id=" + id + ", name=" + name + ", location=" + location + ", bicycles=" + bicycles + "]";
    }

    
}
