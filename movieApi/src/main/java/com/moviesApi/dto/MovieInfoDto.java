package com.moviesApi.dto;

public class MovieInfoDto {

    private boolean hasSeen;
    private int rating;
    
	public MovieInfoDto(boolean hasSeen, int rating) {
		super();
		this.hasSeen = hasSeen;
		this.rating = rating;
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
