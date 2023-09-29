package com.moviesApi.dto;


import java.util.List;
import java.util.Set;

public class UserDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<UserDto> friends;
    private List<Long> moviesId;

    
    public UserDto() {
    }

    
    public UserDto(long id, String firstName, String lastName, String email, String password, Set<UserDto> friends,
            List<Long> moviesId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.friends = friends;
        this.moviesId = moviesId;
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

    public Set<UserDto> getFriends() {
        return friends;
    }

    public void setFriends(Set<UserDto> friends) {
        this.friends = friends;
    }

    public List<Long> getMoviesId() {
        return moviesId;
    }

    public void setMoviesId(List<Long> moviesId) {
        this.moviesId = moviesId;
    }
}
