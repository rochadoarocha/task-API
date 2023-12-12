package br.edu.ifpe.taskapi.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpe.taskapi.dto.create.TaskDTO;
import br.edu.ifpe.taskapi.dto.read.TaskReadDTO;
import br.edu.ifpe.taskapi.dto.update.TaskUpdateDTO;
import br.edu.ifpe.taskapi.entities.Task;
import br.edu.ifpe.taskapi.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class TaskController {
	
	@Autowired
	private TaskService service;
		
		@Operation(summary = "Create Task", description = "Endpoint to create a new Task.")
		@ApiResponses(value = {
	            @ApiResponse(responseCode = "201", description = "Task Successfully created.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Task.class))),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error.")
	    })
		@PostMapping("api/tasks")
		public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO){
			return service.createTask(taskDTO);
		}
		
		
		@Operation(summary = "Get Tasks by UserID", description = "Endpoint that return every task associated to the ID.")
		@ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "OK"),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error")
	    })
		@GetMapping("api/tasks/{id}")
	    public ResponseEntity<?> getTasksByUserId(@PathVariable Integer id) {
	        return service.getTasksByUserId(id);
	    }
		
		
		@Operation(summary = "Update Task", description = "Endpoint to Update an Existing Task.")
		@ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "Task Updated"),
				@ApiResponse(responseCode = "404", description = "Task not_Found by Id"),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error")
	    })
		@PatchMapping("api/tasks/{id}")
		public ResponseEntity<?> updateTask(@PathVariable Integer id, @RequestBody TaskUpdateDTO taskDTO){
			return service.updateTask(id, taskDTO);
			
		}
		

		@Operation(summary = "Delete Task", description = "Endpoint to Delete an Existing Task.")
		@ApiResponses(value = {
	            @ApiResponse(responseCode = "204", description = "Task Deleted"),
				@ApiResponse(responseCode = "404", description = "Task not_Found by Id"),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error")
	    })
		@DeleteMapping("api/tasks/{id}")
		public ResponseEntity<?> deleteTask(@PathVariable Integer id){
			return service.deleteTask(id);
		}
		
		@ResponseStatus(HttpStatus.BAD_REQUEST)
		@ExceptionHandler(MethodArgumentNotValidException.class)
		public Map<String, String> handleValidationException(MethodArgumentNotValidException ex){
			Map<String, String> errors = new HashMap<>();
			ex.getBindingResult().getAllErrors().forEach((error) -> {
				String fieldName = ((FieldError) error).getField();
				String errorMessage = error.getDefaultMessage();
				errors.put(fieldName,errorMessage);
				});
			return errors;
			
		}
	
}
