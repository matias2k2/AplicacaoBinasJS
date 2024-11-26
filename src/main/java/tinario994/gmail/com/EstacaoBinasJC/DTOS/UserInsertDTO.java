package tinario994.gmail.com.EstacaoBinasJC.DTOS;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import tinario994.gmail.com.EstacaoBinasJC.Services.Valid.UserInsertValid;

@Getter
@Setter
@UserInsertValid
public class UserInsertDTO extends UserDTO {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Senha é obrigatória")
    private String password;

    UserInsertDTO() {
        super();
    }
}
