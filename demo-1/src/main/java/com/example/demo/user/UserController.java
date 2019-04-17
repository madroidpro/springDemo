package com.example.demo.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {
	
	@Autowired
	private UserDaoService service;
	
	
	@GetMapping("/Users/all")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
	
	@GetMapping("/Users/{id}")
	public User retrieveOneUser(@PathVariable int id) {
		User user = service.findOne(id);
		if(user == null) {
			throw new UserNotFoundException("id-"+ id);
		}
		return user;
	}
	
	@PostMapping("/Users")
	public ResponseEntity<Object> CreateUser(@RequestBody User user) {
		User saveduser = service.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saveduser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/Users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = service.deletebyId(id);
		if(user == null) {
			throw new UserNotFoundException("id-"+ id);
		}
	}
}
