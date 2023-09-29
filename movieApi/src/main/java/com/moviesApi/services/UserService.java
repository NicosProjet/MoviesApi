package com.moviesApi.services;

import java.util.List;

import com.moviesApi.dto.UserDto;

public interface UserService {
	
	List<UserDto> getAll();
	
	UserDto getById(long id);

	UserDto saveOrUpdate(UserDto uDto);
	
	void delete(long id);

}
