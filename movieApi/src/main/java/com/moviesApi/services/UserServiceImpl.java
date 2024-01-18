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

import com.moviesApi.dto.ChangePwdDto;


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
			System.out.println(result);
			return result;
		} else
			System.out.println("test");
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

	@Override
	public void addFriend(long userId, long friendId) {
		// TODO Auto-generated method stub
		Optional<User> userOptional = userRepository.findById(userId);
		Optional<User> friendOptional = userRepository.findById(friendId);

		if(userOptional.isPresent() && friendOptional.isPresent()) {
			User user = userOptional.get();
			User friend = friendOptional.get();

			if (user.getFriends().contains(friend)) {
				// Les utilisateurs sont déjà amis, renvoie un message approprié
				throw new IllegalArgumentException("Vous êtes déjà amis avec cette personne.");
			}
			
			user.getFriends().add(friend);
			friend.getFriends().add(user);

			

			userRepository.save(user);
			userRepository.save(friend);
		} else {

			if (!userOptional.isPresent()) {

				throw new IllegalArgumentException("L'utilisateur avec l'ID " + userId + " n'existe pas.");
			}

			if (!friendOptional.isPresent()) {
				// L'ami avec friendId n'existe pas
				throw new IllegalArgumentException("L'ami avec l'ID " + friendId + " n'existe pas.");
			}
		}


	}

	@Override
	public void deleteFriend(long userId, long friendId) {
		// TODO Auto-generated method stub
		Optional<User> userOptional = userRepository.findById(userId);
		Optional<User> friendOptional = userRepository.findById(friendId);

		if(userOptional.isPresent() && friendOptional.isPresent() && !friendOptional.isEmpty()) {
			User user = userOptional.get();
			User friend = friendOptional.get();
			// Supprimer l'ami de la liste d'amis de l'utilisateur
			user.getFriends().remove(friend);

			// Supprimer l'utilisateur de la liste d'amis de l'ami
			friend.getFriends().remove(user);

			// Enregistrer les modifications dans la base de données
			userRepository.save(user);
			userRepository.save(friend);
		}else {
			// Gérer le cas où l'id d'une personne n'existe pas
			if (!userOptional.isPresent()) {
				// L'utilisateur avec userId n'existe pas
				throw new IllegalArgumentException("L'utilisateur avec l'ID " + userId + " n'existe pas.");
			}

			if (!friendOptional.isPresent()) {
				// L'ami avec friendId n'existe pas
				throw new IllegalArgumentException("L'ami avec l'ID " + friendId + " n'existe pas.");
			}
		}

	}
	
	@Override
	public boolean resetPassword(ChangePwdDto changePwdObj) throws Exception {
		boolean expired = jwtTokenUtil.isTokenExpired(changePwdObj.getToken());
		if (expired)
			throw new Exception("Error : Expired token, ask for reset again !");

		String newPassword = HashTools.hashSHA512(changePwdObj.getPassword());
		
		//récupérer l'utilisateur par email	
		String email = jwtTokenUtil.getUsernameFromToken(changePwdObj.getToken());
		User u = userRepository.findByEmail(email);
		
		if(u!=null) {
			String currentPwd = u.getPassword();
			
			if(newPassword.equals(currentPwd))
				throw new Exception("Error : updating with the same old password !");
			
			u.setPassword(newPassword);
			userRepository.saveAndFlush(u);
			return true;
		}
		return false;
	}


}
