package br.edu.ifpe.taskapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        http.csrf((csrf) -> csrf.disable())
			.cors(cors -> cors.disable())
        	.authorizeHttpRequests((authorizeHttpRequests) ->
 				authorizeHttpRequests
 					.requestMatchers(HttpMethod.GET,"/swagger-ui/*","/v3/api-docs/*","/v3/api-docs","/swagger-ui.html","api/tasks/*","api/users/*").permitAll()
 					.requestMatchers(HttpMethod.POST,"api/login","api/tasks","api/users").permitAll()
 					.requestMatchers(HttpMethod.DELETE,"api/users/*","api/tasks/*").permitAll()
 					.requestMatchers(HttpMethod.PATCH,"api/tasks/*","api/users/*").permitAll());
        return http.build();
	}
	
	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
