package br.edu.ifpe.taskapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
		public ResponseEntity<Task> createTask(@RequestBody Task task){
			return service.createTask(task);
		}
		
		@GetMapping("api/tasks")
	    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable Integer userId) {
	        return service.getTasksByUserId(userId);
	    }
	
}
