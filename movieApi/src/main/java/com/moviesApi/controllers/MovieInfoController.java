package com.moviesApi.controllers;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviesApi.dto.MovieInfoDto;
import com.moviesApi.services.MovieService;


@RestController
@RequestMapping("/movieApi/movieInfo")
public class MovieInfoController {

	@Autowired
	private MovieService movieService;
	



	@PostMapping("/users/{userId}/movies")
	public ResponseEntity<MovieInfoDto> save(@PathVariable long userId, @RequestBody MovieInfoDto mDto) {
	    try {
	        MovieInfoDto result = movieService.saveOrUpdate(userId, mDto);
	        return ResponseEntity.status(HttpStatus.CREATED).body(result);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}

	//READ
	@GetMapping(produces = "application/json")
	public List<MovieInfoDto> getAll() {
		return movieService.getAll();
	}
	
	@GetMapping(value="/{id}", produces = "application/json")
	public MovieInfoDto getById(@PathVariable("id") long id){
		return movieService.getById(id);
	}


	//UPDATE
//	@PutMapping(consumes="application/json", produces = "application/json")
//	public MovieInfoDto update(@RequestBody MovieInfoDto mDto){
//		return movieService.saveOrUpdate(mDto);
//	}


	//DELETE
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Long> delete(@PathVariable(name = "id")long id) throws Exception{
		try {
			movieService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body(id);
		} catch (Exception e) {

			e.printStackTrace();
			throw new Exception("Erreur suppression impossible");
		}
	}




}
