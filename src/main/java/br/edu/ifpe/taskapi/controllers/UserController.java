package br.edu.ifpe.taskapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import br.edu.ifpe.taskapi.entities.User;
import br.edu.ifpe.taskapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Operation(summary = "Cadastrar Usuário", description = "Endpoint para cadastrar um novo usuário.")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "409", description = "Conflito - Usuário já existe"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
	@PostMapping("api/users")
	public ResponseEntity<User> createUser(@RequestBody User user){
		return service.cadastrarUsuario(user);
	}
	
}