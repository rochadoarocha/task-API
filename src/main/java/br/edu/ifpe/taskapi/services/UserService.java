package br.edu.ifpe.taskapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import br.edu.ifpe.taskapi.entities.User;
import br.edu.ifpe.taskapi.repositories.IUserRepository;


@Service
public class UserService {
	
	@Autowired
	private IUserRepository repository;
	

	@Transactional
	public ResponseEntity<User> cadastrarUsuario (@RequestBody User user){
		try {
			Optional<User> isCreated = repository.findByEmail(user.getEmail());
			if(isCreated.isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}else {
				User newUser = new User(user.getName(),user.getEmail(),user.getPassword());
				repository.save(newUser);
				return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
			}
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}

}
