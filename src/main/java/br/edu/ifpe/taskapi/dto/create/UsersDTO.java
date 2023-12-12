package br.edu.ifpe.taskapi.dto.create;

import org.springframework.beans.BeanUtils;
import br.edu.ifpe.taskapi.entities.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsersDTO {
	
	@NotNull(message="Nome não pode ser Nulo")
	private String name;
	
	@NotNull(message="Email não pode ser Nulo")
	private String email;
	
	@NotNull(message="Senha não pode ser Nula")
	private String password;
	
	public UsersDTO() {
		
	}
	public UsersDTO(User entity) {
		BeanUtils.copyProperties(entity, this);
	}
}
