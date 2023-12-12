package br.edu.ifpe.taskapi.controllers;

import java.util.HashMap;
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
import br.edu.ifpe.taskapi.dto.create.UsersDTO;
import br.edu.ifpe.taskapi.dto.read.UsersLoginDTO;
import br.edu.ifpe.taskapi.dto.update.UserUpdateDTO;
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
	
	
	@Operation(summary = "Create a User", description = "Endpoint to register a new user.")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UsersDTO.class))),
	        @ApiResponse(responseCode = "409", description = "Conflict - User already exists"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")})
	@PostMapping("api/users")
	public ResponseEntity<?> createUser(@RequestBody UsersDTO user){
		return service.createUser(user);
	}

	
	@Operation(summary = "Authenticate a User", description = "Endpoint to authenticate a user and generate an access token.")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Successfully authenticated", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))),
	        @ApiResponse(responseCode = "401", description = "Unauthorized - Incorrect email or password"),
	        @ApiResponse(responseCode = "404", description = "User not found"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")})
	@PostMapping("api/login")
	public ResponseEntity<?>loginUser(@RequestBody UsersLoginDTO userLoginDTO){
		return service.loginUser(userLoginDTO);
		
	}
	
	@Operation(summary = "Delete a User", description = "Endpoint to delete a user by your Id")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "201", description = "Successfully Deleted"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")})
	@DeleteMapping("api/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id){
		return service.deleteUser(id);
		
	}


	@Operation(summary = "Get an User", description = "Endpoint to get an user By your Id")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Successfully Get"),
			 @ApiResponse(responseCode = "404", description = "User Not Found"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")})

	@GetMapping("api/users/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Integer id){
		return service.getUserById(id);
	}


	@Operation(summary = "Update an User", description = "Endpoint to update a user")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Successfully Updated"),
			 @ApiResponse(responseCode = "404", description = "User Not Found"),
			 @ApiResponse(responseCode = "409", description = "Email already in Use"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")})
	@PatchMapping("api/users/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserUpdateDTO userUpdateDto){
		return service.updateUser(id,userUpdateDto);
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
			});
		return errors;
		
	}
}
