package com.moviesApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moviesApi.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	
	@Query("FROM User u WHERE u.email= :email") //si SQL native, mettre nativeQuery=true
	User findByEmail(@Param("email") String email);
}
