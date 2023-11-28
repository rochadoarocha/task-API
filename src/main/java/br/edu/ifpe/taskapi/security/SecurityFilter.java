package br.edu.ifpe.taskapi.security;

import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class SecurityFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getHeader("Authorization") != null) {
			try {
				 Authentication auth = TokenUtil.validate(request);
	             SecurityContextHolder.getContext().setAuthentication(auth);
			}catch (IllegalArgumentException e) {            
		         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		         response.setContentType("application/json");
		         response.getWriter().write("{ \"error\": " + e.getMessage()+ "}");
		         return;
			}
               
		}
        	filterChain.doFilter(request, response);
    	}
}