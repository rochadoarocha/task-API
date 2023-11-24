package br.edu.ifpe.taskapi.doc;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Task-API",
        version = "1.0",
        description = "API de gerenciamento de tarefas para a cadeira de Desenvolvimento WEB 2."
    )
)
public class SwaggerConfig {
	
}
