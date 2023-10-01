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
	        // Le film est nouveau, sauvegardez-le pour lui attribuer un ID
	        m = movieRepository.saveAndFlush(m);
	        mDto.setId(m.getId());

	        // Ensuite, ajoutez le film à la liste de films de l'utilisateur
	        addMovieToUser(userId, mDto);
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
	public Map<Long, MovieInfoDto> addMovieToUser(long userId, MovieInfoDto movieInfoDto) {
		Optional<User> userOptional = userRepository.findById(userId);

		if (userOptional.isPresent()) {
			User user = userOptional.get();

			// Récupérez la map des films de l'utilisateur
			Map<Long, MovieInfo> userMoviesInfo = user.getMoviesInfo();
			System.out.println(userMoviesInfo);

			if (!userMoviesInfo.containsKey(movieInfoDto.getId())) {

				MovieInfo movieInfo = DtoTools.convert(movieInfoDto, MovieInfo.class);

				userMoviesInfo.put(movieInfo.getId(), movieInfo);

				System.out.println(user);
				userRepository.save(user);

				// Convertissez la liste de MovieInfo en MovieInfoDto
				Map<Long, MovieInfoDto> userMoviesInfoDto = new HashMap<>();
				for (Map.Entry<Long, MovieInfo> entry : userMoviesInfo.entrySet()) {
					MovieInfo movie = entry.getValue();
					MovieInfoDto movieDto = DtoTools.convert(movie, MovieInfoDto.class);
					userMoviesInfoDto.put(entry.getKey(), movieDto);
				}

				// Retournez la nouvelle liste de films de l'utilisateur
				return userMoviesInfoDto;
			} else {
				// Gérez le cas où le film existe déjà dans la liste
				throw new IllegalArgumentException("Le film existe déjà dans la liste de l'utilisateur.");
			}
		} else {
			// Gérez le cas où l'utilisateur n'existe pas
			throw new IllegalArgumentException("L'utilisateur avec l'ID " + userId + " n'existe pas.");
		}
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



