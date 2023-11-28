package br.edu.ifpe.taskapi.dto.read;

import org.springframework.beans.BeanUtils;

import br.edu.ifpe.taskapi.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsersLoginDTO {
	
	@NotNull(message="Email não pode ser Nulo")
	@Email(message="Email Inválido")
	private String email;
	
	@NotNull(message="Senha não pode ser Nula")
	private String password;
	
	
	
	public UsersLoginDTO() {
		
	}
	public UsersLoginDTO(User entity) {
		BeanUtils.copyProperties(entity, this);
	}
}
