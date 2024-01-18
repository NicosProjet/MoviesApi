package com.moviesApi.dto;

public class MovieInfoDto {

	private Long id;
	private boolean hasSeen;
	private int rating;
	private UserDto user;



	public MovieInfoDto() {
		super();
	}

	public MovieInfoDto(Long id, boolean hasSeen, int rating, UserDto user) {
		super();
		this.id = id;
		this.hasSeen = hasSeen;
		this.rating = rating;
		this.user = user;
	}


	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isHasSeen() {
		return hasSeen;
	}

	public void setHasSeen(boolean hasSeen) {
		this.hasSeen = hasSeen;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	} 





}
