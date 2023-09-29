package com.moviesApi.services;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviesApi.dto.DtoTools;
import com.moviesApi.dto.UserDto;
import com.moviesApi.entities.User;
import com.moviesApi.repositories.UserRepository;
import com.moviesApi.tools.HashTools;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

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

}
