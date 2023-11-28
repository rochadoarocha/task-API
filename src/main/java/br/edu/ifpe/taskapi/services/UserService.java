package br.edu.ifpe.taskapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import br.edu.ifpe.taskapi.dto.create.UsersDTO;
import br.edu.ifpe.taskapi.dto.read.UsersLoginDTO;
import br.edu.ifpe.taskapi.entities.User;
import br.edu.ifpe.taskapi.repositories.IUserRepository;
import br.edu.ifpe.taskapi.security.Token;
import br.edu.ifpe.taskapi.security.TokenUtil;


@Service
public class UserService {
	
	@Autowired
	private IUserRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Transactional
	public ResponseEntity<UsersDTO> createUser (@RequestBody UsersDTO userDTO){
		try {
			Optional<User> isCreated = repository.findByEmail(userDTO.getEmail());
			if(isCreated.isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}else {
				String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
				User newUser = new User(userDTO.getName(),userDTO.getEmail(),encryptedPassword);
				repository.save(newUser);
				return ResponseEntity.status(HttpStatus.CREATED).build();
			}
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	public ResponseEntity<?> loginUser(@RequestBody UsersLoginDTO userLoginDTO) {	
		try {
			String email = userLoginDTO.getEmail();
			String password = userLoginDTO.getPassword();
			Optional<User> userToLogin = repository.findByEmail(email);
			if (userToLogin.isPresent()) {
			    if (passwordEncoder.matches(password, userToLogin.get().getPassword())) {
						String tokenAuth = TokenUtil.createToken(userToLogin.get().getEmail());
						Token token = new Token(tokenAuth);
						userToLogin.get().setPassword("");
						return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.AUTHORIZATION,token.getToken()).body(userToLogin);
				}else{
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou Senha incorretos!");
					}
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			
		}catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
}
