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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpe.taskapi.dto.create.TaskDTO;
import br.edu.ifpe.taskapi.dto.read.TaskReadDTO;
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
		
		@Operation(summary = "Criar Tarefa", description = "Endpoint para criar uma nova tarefa.")
		@ApiResponses(value = {
	            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Task.class))),
	            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
	    })
		
		@PostMapping("api/tasks")
		public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO){
			return service.createTask(taskDTO);
		}
		
		@GetMapping("api/tasks/{id}")
	    public ResponseEntity<List<TaskReadDTO>> getTasksByUserId(@PathVariable Integer id) {
	        return service.getTasksByUserId(id);
	    }
		
		
		@ResponseStatus(HttpStatus.BAD_REQUEST)
		@ExceptionHandler(MethodArgumentNotValidException.class)
		public Map<String, String> handleValidationException(MethodArgumentNotValidException ex){
			Map<String, String> errors = new HashMap<>();
			ex.getBindingResult().getAllErrors().forEach((error) -> {
				String fieldName = ((FieldError) error).getField();
				String errorMessage = error.getDefaultMessage();
				errors.put(fieldName,errorMessage);
				errors.put(fieldName,errorMessage);
				});
			return errors;
			
		}
	
}
