package com.moviesApi.interceptors;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.moviesApi.entities.User;
import com.moviesApi.exceptions.TokenException;
import com.moviesApi.repositories.UserRepository;
import com.moviesApi.tools.JwtTokenUtil;
import com.moviesApi.tools.TokenSaver;


@Component
public class TokenInterceptor implements HandlerInterceptor {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserRepository userRepository;

	
	@Override	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(!request.getMethod().equals("OPTIONS")) {//on ne doit pas interception les req : OPTIONS
			
			if(!request.getRequestURI().equals("/login")
				&& !request.getRequestURI().equals("/reset-password")
				&& !request.getRequestURI().equals("/change-password")
					) {//ni les req /login
//				récupérer l'entête Authorization (bearer TOKENNNNN)
				String headerAuth = request.getHeader("Authorization");
				// si aucun jeton ou non conforme <7 => exception jeton absent ou non valide
				if(headerAuth==null || headerAuth.trim().equals("") || headerAuth.length()<7)
					throw new TokenException("Error : Invalid token !");
				
				//	découpe pour extraire le jeton
				String token = headerAuth.substring(7);
				//	valider le jeton en vérifiant qu'il n'a pas expiré
				if(jwtTokenUtil.isTokenExpired(token))
					throw new TokenException("Error : Expired token !");
				
				//vérifier que c'est un jeton qui a été stocké dans la map (TokenSaver)
				String email = jwtTokenUtil.getUsernameFromToken(token);
				if(!TokenSaver.tokensByEmail.containsKey(email) 
						|| !TokenSaver.tokensByEmail.get(email).equals(token))
					throw new TokenException("Error : Unknown token !");
				
				
		
			}
		}
		
		
		return true;
	}

	
}
