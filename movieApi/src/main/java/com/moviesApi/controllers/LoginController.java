package com.moviesApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.moviesApi.dto.LoginDto;
import com.moviesApi.dto.LoginResponseDto;
import com.moviesApi.services.UserService;


@RestController
public class LoginController {

	@Autowired
	private UserService userService;
	
	@PostMapping(value="/login", consumes="application/json", produces="application/json")
	 public LoginResponseDto checkLogin(@RequestBody LoginDto loginDto) throws Exception {
		
	        return userService.checkLogin(loginDto);
	 }
	
}
