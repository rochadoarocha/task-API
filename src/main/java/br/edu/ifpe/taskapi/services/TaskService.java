package br.edu.ifpe.taskapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.edu.ifpe.taskapi.entities.Task;
import br.edu.ifpe.taskapi.repositories.ITaskRepository;
import br.edu.ifpe.taskapi.repositories.IUserRepository;

@Service
public class TaskService {
	@Autowired
	private ITaskRepository taskRepository;
	
	@Autowired
	private IUserRepository userRepository;
	

	@Transactional
	public ResponseEntity<Task> createTask (@RequestBody Task task){
		try {
		 Task newTask = new Task(task.getTitle(),task.getDescription(), false, task.getUser());
		 taskRepository.save(newTask);
		 return ResponseEntity.status(HttpStatus.CREATED).body(newTask);			
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}		
	}
	
	@Transactional(readOnly = true)
	public ResponseEntity <List<Task>> getTasksByUserId(@PathVariable Integer userId) {
		try {
			List<Task> taskByUserId = taskRepository.FindTaskByUserId(userId);
			return ResponseEntity.status(HttpStatus.OK).body(taskByUserId);
			} catch(Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	@Transactional
	public ResponseEntity<Task> updateTask (@RequestBody Task task) {
		try {
			
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return null;
	}
	
	@Transactional
	public ResponseEntity<Task> deleteTask (@RequestBody Task task) {
		try {
			
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return null;
	}

}
