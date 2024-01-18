package com.moviesApi.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.moviesApi.dto.DtoTools;
import com.moviesApi.dto.MovieInfoDto;
import com.moviesApi.entities.MovieInfo;
import com.moviesApi.entities.User;
import com.moviesApi.repositories.MovieRepository;
import com.moviesApi.repositories.UserRepository;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MovieRepository movieRepository;
	
	
	@Override
	public MovieInfoDto saveOrUpdate(long userId, MovieInfoDto mDto) {
	    MovieInfo m = DtoTools.convert(mDto, MovieInfo.class);

	    // Vérifiez si l'ID du film est égal à 0
	    if (m.getId() == 0) {
	        
	        Optional<User> userOptional = userRepository.findById(userId);

	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            m.setUser(user);  // Associer le film à l'utilisateur
	            m = movieRepository.saveAndFlush(m);
	            mDto.setId(m.getId());
	        } else {
	            throw new IllegalArgumentException("L'utilisateur avec l'ID " + userId + " n'existe pas.");
	        }
	    } else {
	        // Le film existe déjà, vérifiez s'il appartient à l'utilisateur
	        Optional<User> userOptional = userRepository.findById(userId);

	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            Map<Long, MovieInfo> userMoviesInfo = user.getMoviesInfo();

	            if (userMoviesInfo.containsKey(m.getId())) {
	                // Mettez à jour les propriétés du film existant avec celles du DTO
	                MovieInfo existingMovie = userMoviesInfo.get(m.getId());
	                existingMovie.setRating(m.getRating());
	                existingMovie.setHasSeen(m.isHasSeen());

	                // Enregistrez les modifications dans la base de données
	                movieRepository.saveAndFlush(existingMovie);
	            } else {
	                throw new IllegalArgumentException("Le film n'appartient pas à l'utilisateur.");
	            }
	        } else {
	            throw new IllegalArgumentException("L'utilisateur avec l'ID " + userId + " n'existe pas.");
	        }
	    }

	    return mDto;
	}



	



	@Override
	public List<MovieInfoDto> getAll() {
		List<MovieInfo> moviesInfo = movieRepository.findAll();
		List<MovieInfoDto> result = new ArrayList<MovieInfoDto>();
		for (MovieInfo m : moviesInfo) {
			result.add(DtoTools.convert(m, MovieInfoDto.class));
		}
		return result;
	}

	@Override
	public MovieInfoDto getById(long id) {
		Optional<MovieInfo> m = movieRepository.findById(id);
		if (m.isPresent())
			return DtoTools.convert(m.get(), MovieInfoDto.class);

		return null;
	}


	@Override
	public void delete(long id) {
		movieRepository.deleteById(id);
	}
}



