package com.moviesApi.services;

import java.util.List;
import java.util.Map;

import com.moviesApi.dto.MovieInfoDto;



public interface MovieService {
	
	Map<Long, MovieInfoDto> addMovieToUser(long userId, MovieInfoDto movieInfo);

	
	List<MovieInfoDto> getAll();
	
	MovieInfoDto getById(long id);

	MovieInfoDto saveOrUpdate(long userId, MovieInfoDto uDto);
	
	void delete(long id);
}
