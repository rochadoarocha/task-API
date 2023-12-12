package br.edu.ifpe.taskapi.dto.update;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {

    @NotNull(message="Senha não pode ser Nula")
    private String password;

    public UserUpdateDTO(){
        
    }

}
