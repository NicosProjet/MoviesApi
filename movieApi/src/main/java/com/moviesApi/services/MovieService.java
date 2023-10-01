package com.moviesApi.services;

import java.util.Map;

import com.moviesApi.dto.MovieInfoDto;


public interface MovieService {
	
	Map<Long, MovieInfoDto> addMovieToUser(long userId, MovieInfoDto movieInfo);

}
