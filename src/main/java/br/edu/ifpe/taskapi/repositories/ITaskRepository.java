package br.edu.ifpe.taskapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifpe.taskapi.entities.Task;

public interface ITaskRepository extends JpaRepository<Task, Integer> {
	
	@Query(value = "SELECT * FROM task WHERE user_id = ?1", nativeQuery = true)
	List<Task> FindTaskByUserId(Integer id);

}