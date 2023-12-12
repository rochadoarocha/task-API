package br.edu.ifpe.taskapi.dto.read;

import org.springframework.beans.BeanUtils;

import br.edu.ifpe.taskapi.entities.Task;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskReadDTO {
	
	private Integer id;
	
	
	private String title;
	
	
	private String description;
	

	private boolean status;
	
    private UserMinDTO userMinDTO;
	
	public TaskReadDTO(Task entity) {
		BeanUtils.copyProperties(entity, this);
		
	}

	public TaskReadDTO (Integer id, String title, String description, Boolean status, UserMinDTO userminDTO) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = status;
		this.userMinDTO = userminDTO;
	}
	
	public TaskReadDTO () {
		
	}



}


