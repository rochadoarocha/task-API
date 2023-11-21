package br.edu.ifpe.taskapi.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ifpe.taskapi.entities.User;

public interface IUserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);
}

