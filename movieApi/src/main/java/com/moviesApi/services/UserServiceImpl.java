package com.moviesApi.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviesApi.dto.DtoTools;
import com.moviesApi.dto.LoginDto;
import com.moviesApi.dto.LoginResponseDto;
import com.moviesApi.dto.UserDto;
import com.moviesApi.entities.User;
import com.moviesApi.repositories.UserRepository;
import com.moviesApi.tools.HashTools;
import com.moviesApi.tools.JwtTokenUtil;
import com.moviesApi.tools.TokenSaver;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public UserDto saveOrUpdate(UserDto uDto) {
		
		User u = DtoTools.convert(uDto, User.class);
		try {
			if (u.getId() == 0) { // insertion
				
				u.setPassword(HashTools.hashSHA512(u.getPassword()));
			} else { // modif
				UserDto userInDb = getById(u.getId());
				if (!userInDb.getPassword().contentEquals(u.getPassword())) {
					u.setPassword(HashTools.hashSHA512(u.getPassword()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		u = userRepository.saveAndFlush(u);
		
		return DtoTools.convert(u, UserDto.class);

	}

	@Override
	public List<UserDto> getAll() {
		List<User> users = userRepository.findAll();
		List<UserDto> result = new ArrayList<UserDto>();
		for (User u : users) {
			result.add(DtoTools.convert(u, UserDto.class));
		}
		return result;
	}


	@Override
	public UserDto getById(long id) {
		Optional<User> u = userRepository.findById(id);
		if (u.isPresent())
			return DtoTools.convert(u.get(), UserDto.class);

		return null;
	}
	
	@Override
	public void delete(long id) {
		userRepository.deleteById(id);
	}
	
	
	@Override
	public LoginResponseDto checkLogin(LoginDto loginDto) throws Exception {
		User u = userRepository.findByEmail(loginDto.getEmail());
		if (u != null && u.getPassword().equals(HashTools.hashSHA512(loginDto.getPassword()))) {
			LoginResponseDto result = DtoTools.convert(u, LoginResponseDto.class);
			// generate JWT TOKEN
			Map<String, Object> claims = new HashMap<String, Object>();
			claims.put("user_id", u.getId());
			claims.put("user_fullName", u.getFirstName() + " " + u.getLastName());


			String token = jwtTokenUtil.doGenerateToken(claims, loginDto.getEmail());
			TokenSaver.tokensByEmail.put(u.getEmail(), token);
			// générer le token
			// le sauvegarder côté service pour pouvoir le vérifier lors des prochaines
			// requêtes
			result.setToken(token);
			return result;
		} else
			throw new Exception("Error : invalid credentials !");
	}

	@Override
	public UserDto findByEmail(String email) throws Exception {
		// TODO Auto-generated method stub
		User userByEmail = userRepository.findByEmail(email);
		
		if(userByEmail != null) {
			
			return DtoTools.convert(userByEmail, UserDto.class);
		}
		else {
			throw new Exception("User not found !");
		}
		
	}
	

}
