package com.moviesApi.services;

import java.util.List;

import com.moviesApi.dto.LoginDto;
import com.moviesApi.dto.LoginResponseDto;
import com.moviesApi.dto.UserDto;


public interface UserService {
	
	List<UserDto> getAll();
	
	UserDto getById(long id);

	UserDto saveOrUpdate(UserDto uDto);
	
	void delete(long id);
	
	LoginResponseDto checkLogin(LoginDto loginDto) throws Exception;
	
	UserDto findByEmail(String email) throws Exception;
	
	void addFriend(long userId, long friendId);
	
	void deleteFriend(long userId, long friendId);
	

}
