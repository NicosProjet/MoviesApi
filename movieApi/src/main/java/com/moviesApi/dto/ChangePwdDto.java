package com.moviesApi.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ChangePwdDto implements Serializable {

	private String token;
	private String password;

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


}
