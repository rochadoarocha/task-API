package br.edu.ifpe.taskapi.dto.read;

import org.springframework.beans.BeanUtils;

import br.edu.ifpe.taskapi.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMinDTO {
	
	private Integer id;
	private String name;
	
	public UserMinDTO(User entity) {
		BeanUtils.copyProperties(entity, this);
	}
	
	public UserMinDTO() {
		
	}
}
