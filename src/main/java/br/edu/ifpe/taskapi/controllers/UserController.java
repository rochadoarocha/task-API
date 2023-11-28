package br.edu.ifpe.taskapi.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpe.taskapi.dto.UsersDTO;
import br.edu.ifpe.taskapi.dto.UsersLoginDTO;
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
	
	
	@Operation(summary = "Create a User", description = "Endpoint to register a new user.")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UsersDTO.class))),
	        @ApiResponse(responseCode = "409", description = "Conflict - User already exists"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")})
	@PostMapping("api/users")
	public ResponseEntity<UsersDTO> createUser(@RequestBody UsersDTO user){
		return service.createUser(user);
	}

	
	@Operation(summary = "Authenticate a User", description = "Endpoint to authenticate a user and generate an access token.")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Successfully authenticated", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UsersDTO.class))),
	        @ApiResponse(responseCode = "401", description = "Unauthorized - Incorrect email or password"),
	        @ApiResponse(responseCode = "404", description = "User not found"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")})
	@PostMapping("api/login")
	public ResponseEntity<?>LoginUser(@RequestBody UsersLoginDTO userLoginDTO){
		return service.loginUser(userLoginDTO);
		
	}
	
	@GetMapping("api/token")
	public String token () {
		return "Voce acessou o endpoint protegido";
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
