package com.moviesApi.dto;



import java.util.Map;

import com.moviesApi.entities.MovieInfo;


public class UserDto {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    // Map pour stocker les informations sur les films (id du film -> MovieInfo)
 // Convertisseur personnalisé
    private Map<Long, MovieInfo> moviesInfo;

    
    public UserDto() {
    }


	public UserDto(long id, String firstName, String lastName, String email, String password,
			Map<Long, MovieInfo> moviesInfo) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.moviesInfo = moviesInfo;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Map<Long, MovieInfo> getMoviesInfo() {
		return moviesInfo;
	}


	public void setMoviesInfo(Map<Long, MovieInfo> moviesInfo) {
		this.moviesInfo = moviesInfo;
	}

    
   
}
