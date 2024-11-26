package tinario994.gmail.com.EstacaoBinasJC.DTOS;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import tinario994.gmail.com.EstacaoBinasJC.Models.Message;

@Getter
@Setter
public class MessageDTO {

    private Integer id;
    private List<UserDTO> sender = new ArrayList<>();
    private List<UserDTO> receiver = new ArrayList<>();
    private String content;
    private String timestamp;
    private String type;

    private int points;
    public MessageDTO() {}
    public MessageDTO(Integer id, List<UserDTO> sender, List<UserDTO> receiver, String content, String timestamp,
            String type, int points) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = timestamp;
        this.type = type;
        this.points = points;
    }

    public MessageDTO(Message entity) {
        this.id = entity.getId();
        entity.getSender().forEach(user -> this.sender.add(new UserDTO(user)));
        entity.getReceiver().forEach(user -> this.receiver.add(new UserDTO(user)));
        this.content = entity.getContent();
        this.timestamp = entity.getTimestamp();
        this.type = entity.getType();
        this.points = entity.getPoints();
    }

}
