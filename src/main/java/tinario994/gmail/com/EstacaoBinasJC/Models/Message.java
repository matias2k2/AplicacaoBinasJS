package tinario994.gmail.com.EstacaoBinasJC.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Messages")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer id;

    @ManyToMany
    @JoinTable(
        name = "message_sender",
        joinColumns = @JoinColumn(name = "message_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> sender = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "message_receiver",
        joinColumns = @JoinColumn(name = "message_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> receiver = new ArrayList<>();

    @Column(nullable = false)
    private String content;

    private String timestamp;

    private String type;

    private int points;

    public Message() {}

    public Message(Integer id, String content, String timestamp, String type, int points) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.type = type;
        this.points = points;
    }
}
