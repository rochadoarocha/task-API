package br.edu.ifpe.taskapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpe.taskapi.entities.User;
import br.edu.ifpe.taskapi.services.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService service;
	
//	Cadastro de usuário
	@PostMapping("api/users")
	public ResponseEntity<User> createUser(@RequestBody User user){
		return service.cadastrarUsuario(user);
	}
	
	@GetMapping("/ola")
    public String ola() {
        return "Olá";
    }
}
