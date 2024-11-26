package tinario994.gmail.com.EstacaoBinasJC.DTOS;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import tinario994.gmail.com.EstacaoBinasJC.Models.Role;

@Getter
@Setter

public class RoleDTOS implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String authority;

    public RoleDTOS() {
    }

    public RoleDTOS(Integer id, String authority) {
        super();
        this.id = id;
        this.authority = authority;
    }

    public RoleDTOS(Role entity) {
        super();
        this.id = entity.getId();
        this.authority = entity.getAuthority();
    }
}
