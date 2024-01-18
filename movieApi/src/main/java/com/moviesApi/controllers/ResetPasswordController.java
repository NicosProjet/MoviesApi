package com.moviesApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviesApi.dto.ResetPasswordDto;
import com.moviesApi.dto.UserDto;
import com.moviesApi.services.SendEmailService;
import com.moviesApi.services.UserService;

import com.moviesApi.dto.ChangePwdDto;
import com.moviesApi.tools.TokenSaver;


@RestController
@RequestMapping("/movieApi")
public class ResetPasswordController {
	@Autowired
	private SendEmailService sendEmailService;
	
	@Autowired
	private UserService userService;
	
	@PutMapping(value = "/reset-password", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDto resetObj) throws Exception {

		UserDto uDto = userService.findByEmail(resetObj.getEmail());

		if (uDto == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		else {
			sendEmailService.sendEmailForResetPassword(uDto);
			return ResponseEntity.status(HttpStatus.OK).body("Email sent for update");
		}
	}
	
	@PutMapping(value = "/change-password", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> changePassword(@RequestBody ChangePwdDto changePwdObj) throws Exception {

		boolean isReferencedToken = TokenSaver.tokensByEmail.containsValue(changePwdObj.getToken());

		if (isReferencedToken) {
			// modifier le mot de passe
			boolean resetStatus = userService.resetPassword(changePwdObj);
			if (resetStatus)
				return ResponseEntity.status(HttpStatus.OK).body("Password updated !");
			else
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Operation not acceptable !");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid token");
		}

	}
	
}
