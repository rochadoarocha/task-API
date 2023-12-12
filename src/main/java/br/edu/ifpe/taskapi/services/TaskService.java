package br.edu.ifpe.taskapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.edu.ifpe.taskapi.dto.create.TaskDTO;
import br.edu.ifpe.taskapi.dto.read.TaskReadDTO;
import br.edu.ifpe.taskapi.dto.read.UserMinDTO;
import br.edu.ifpe.taskapi.dto.update.TaskUpdateDTO;
import br.edu.ifpe.taskapi.entities.Task;
import br.edu.ifpe.taskapi.entities.User;
import br.edu.ifpe.taskapi.repositories.ITaskRepository;
import br.edu.ifpe.taskapi.repositories.IUserRepository;

@Service
public class TaskService {
	@Autowired
	private ITaskRepository taskRepository;
	
	@Autowired
	private IUserRepository userRepository;
	

	@Transactional
	public ResponseEntity<?> createTask (@RequestBody TaskDTO taskDTO){
		try {
		 User user = userRepository.findById(taskDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("User Not Found"));
		 Task newTask = new Task(taskDTO.getTitle(),taskDTO.getDescription(), false, user);
		 taskRepository.save(newTask);
		 return ResponseEntity.status(HttpStatus.CREATED).body(newTask);			
		} catch (IllegalArgumentException e1) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e1.getMessage());
		}
		
	}
	
	@Transactional(readOnly = true)
	public ResponseEntity <List<TaskReadDTO>> getTasksByUserId(@PathVariable Integer userId) {
		try {
			List<Task> taskByUserId = taskRepository.FindTaskByUserId(userId);
			List<TaskReadDTO> taskReadDTOList = taskByUserId.stream()
		            .map(task -> {
		                TaskReadDTO taskReadDTO = new TaskReadDTO();
		                BeanUtils.copyProperties(task, taskReadDTO);		              
		                UserMinDTO userMinDTO = new UserMinDTO();
		                BeanUtils.copyProperties(task.getUser(), userMinDTO);		   
		                taskReadDTO.setUserMinDTO(userMinDTO);
		                return taskReadDTO;
		            })
		            .collect(Collectors.toList());

			return ResponseEntity.status(HttpStatus.OK).body(taskReadDTOList);
			} catch(Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	@Transactional
	public ResponseEntity<?> updateTask(@PathVariable Integer taskId, @RequestBody TaskUpdateDTO updatedTaskDTO) {
	    try {
	        Optional<Task> existingTaskOptional = taskRepository.findById(taskId);
	        if (existingTaskOptional.isPresent()) {
	            Task existingTask = existingTaskOptional.get();

	            existingTask.setTitle(updatedTaskDTO.getTitle());
	            existingTask.setDescription(updatedTaskDTO.getDescription());
	            existingTask.setStatus(updatedTaskDTO.getStatus());

	            TaskReadDTO taskReadDTO = new TaskReadDTO();
                BeanUtils.copyProperties(existingTask, taskReadDTO);		              
                UserMinDTO userMinDTO = new UserMinDTO();
                BeanUtils.copyProperties(existingTask.getUser(), userMinDTO);		   
                taskReadDTO.setUserMinDTO(userMinDTO);
 
                
	            return ResponseEntity.status(HttpStatus.OK).body(taskReadDTO);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task Not Found");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
	}
	
	@Transactional
	public ResponseEntity<Task> deleteTask (@PathVariable Integer id) {
		try {
			taskRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
