package br.edu.ifpe.taskapi.dto.create;

import org.springframework.beans.BeanUtils;

import br.edu.ifpe.taskapi.entities.Task;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {
	
	
	@NotNull(message="Nome não pode ser Nulo")
	private String title;
	
	@NotNull(message="Descrição não pode ser Nulo")
	private String description;
	
	@NotNull(message=" não pode ser Nulo")
	private Boolean status;
	
	@NotNull(message="Nome não pode ser Nulo")
    private Integer userId;
	
	public TaskDTO(Task entity) {
		BeanUtils.copyProperties(entity, this);
		
	}
	
	public TaskDTO () {
		
	}

	
	
}
