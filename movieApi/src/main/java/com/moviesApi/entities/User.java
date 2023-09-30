package com.moviesApi.entities;



import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import com.moviesApi.tools.MovieInfoConverter;

@SuppressWarnings("serial")
@Entity
public class User implements Serializable {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // Map pour stocker les informations sur les films (id du film -> MovieInfo)
    @ElementCollection
    @CollectionTable(name = "user_movies")
    @MapKeyColumn(name = "movie_id")
    @Convert(converter = MovieInfoConverter.class) // Convertisseur personnalisé
    private Map<Long, MovieInfo> moviesInfo;

    public User() {

    }

    public User(long id, String firstName, String lastName, String email, String password,
            Map<Long, MovieInfo> moviesInfo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.moviesInfo = moviesInfo;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<Long, MovieInfo> getMoviesInfo() {
		return moviesInfo;
	}

	public void setMoviesInfo(Map<Long, MovieInfo> moviesInfo) {
		this.moviesInfo = moviesInfo;
	}
    
    
	
	
}
