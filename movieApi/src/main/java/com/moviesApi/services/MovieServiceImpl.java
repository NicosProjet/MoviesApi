package com.moviesApi.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.moviesApi.dto.DtoTools;
import com.moviesApi.dto.MovieInfoDto;
import com.moviesApi.entities.MovieInfo;
import com.moviesApi.entities.User;
import com.moviesApi.repositories.UserRepository;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private UserRepository userRepository;

	@Override
    public Map<Long, MovieInfoDto> addMovieToUser(long userId, MovieInfoDto movieInfoDto) {
        Optional<User> userOptional = userRepository.findById(userId);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Récupérez la map des films de l'utilisateur
            Map<Long, MovieInfo> userMoviesInfo = user.getMoviesInfo();
            

            if (!userMoviesInfo.containsKey(movieInfoDto.getId())) {

                MovieInfo movieInfo = DtoTools.convert(movieInfoDto, MovieInfo.class);
                
                userMoviesInfo.put(movieInfo.getId(), movieInfo);

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
}



