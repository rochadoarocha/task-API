package br.edu.ifpe.taskapi.dto.update;

import org.springframework.beans.BeanUtils;

import br.edu.ifpe.taskapi.entities.Task;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskUpdateDTO {
	
	@NotNull(message="Nome não pode ser Nulo")
	private String title;
	
	@NotNull(message="Descrição não pode ser Nulo")
	private String description;
	
	@NotNull(message=" não pode ser Nulo")
	private Boolean status;
	
	public TaskUpdateDTO(Task entity) {
		BeanUtils.copyProperties(entity, this);
		
	}
	
	public TaskUpdateDTO () {
		
	}

	

}
