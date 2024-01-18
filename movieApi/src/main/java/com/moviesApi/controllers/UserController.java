package com.moviesApi.controllers;

import java.util.List;

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

import com.moviesApi.dto.UserDto;
import com.moviesApi.services.SendEmailService;
import com.moviesApi.services.UserService;


@RestController
@RequestMapping("/movieApi/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SendEmailService sendEmailservice;

	
	//CRUD pour l'entité USER
	
	//CREATE
	@PostMapping(consumes="application/json", produces = "application/json")
	public ResponseEntity<UserDto> save(@RequestBody UserDto uDto){
		
		String to = uDto.getEmail();
		
        String subject = "Bienvenue sur l'application";
        String body = "Bonjour " + uDto.getFirstName() + " " +uDto.getLastName()+ ",\n\nBienvenue sur notre application.";
        
        UserDto result = userService.saveOrUpdate(uDto);
        sendEmailservice.sendEmail(to, subject, body);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(result);
	}
	

	@GetMapping(produces = "application/json")
	public List<UserDto> getAll() {
		return userService.getAll();
	}
	
	//READ
	@GetMapping(value="/{id}", produces = "application/json")
	public UserDto getById(@PathVariable("id") long id){
		return userService.getById(id);
	}
	
	//UPDATE
	@PutMapping(consumes="application/json", produces = "application/json")
	public UserDto update(@RequestBody UserDto uDto){
		return userService.saveOrUpdate(uDto);
	}
	
	//DELETE
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Long> delete(@PathVariable(name = "id")long id) throws Exception{
		try {
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (Exception e) {
          
            e.printStackTrace();
            throw new Exception("Erreur suppression impossible");
        }
	}
	
	@PostMapping("/{userId}/add-friend/{friendId}")
    public ResponseEntity<String> addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        try {
            userService.addFriend(userId, friendId);
            return ResponseEntity.ok("Ami ajouté avec succès.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'ajout de l'ami : " + e.getMessage());
        }
    }
	

    @DeleteMapping("/{userId}/delete-friend/{friendId}")
    public ResponseEntity<String> deleteFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        try {
            userService.deleteFriend(userId, friendId);
            return ResponseEntity.ok("Ami supprimé avec succès.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la suppression de l'ami : " + e.getMessage());
        }
    }
	
}
