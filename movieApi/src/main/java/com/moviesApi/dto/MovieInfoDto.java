package com.moviesApi.dto;

public class MovieInfoDto {

	private Long id;
    private boolean hasSeen;
    private int rating;
    


	public MovieInfoDto(Long id, boolean hasSeen, int rating) {
		super();
		this.id = id;
		this.hasSeen = hasSeen;
		this.rating = rating;
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
