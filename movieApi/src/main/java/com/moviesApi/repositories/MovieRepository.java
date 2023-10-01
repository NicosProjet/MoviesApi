package com.moviesApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moviesApi.entities.MovieInfo;

@Repository
public interface MovieRepository extends JpaRepository<MovieInfo, Long>{

}
