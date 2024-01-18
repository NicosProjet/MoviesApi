package com.moviesApi.services;

import com.moviesApi.dto.UserDto;

public interface SendEmailService {
	
	void sendEmail(String to, String sub, String body);
	void sendEmailForResetPassword(UserDto uDto) throws Exception;
	
}
