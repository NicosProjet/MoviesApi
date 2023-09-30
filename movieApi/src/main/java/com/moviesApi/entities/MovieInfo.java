package com.moviesApi.entities;

import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class MovieInfo {
	
    private boolean hasSeen;
    private int rating; 

    public MovieInfo(boolean hasSeen, int rating) {
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