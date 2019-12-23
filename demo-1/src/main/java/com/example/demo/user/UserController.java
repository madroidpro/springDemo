package com.example.demo.user;

import java.io.FileNotFoundException;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.user.service.ReportService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/Users")
public class UserController {
	
	@Autowired
	private UserDaoService service;
	
	@Autowired
	private ReportService reportService;
	
	@GetMapping("/all")
	public MappingJacksonValue retrieveAllUsers(){
		List<User> users = service.findAll();
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name");
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(users);
		mapping.setFilters(filters);
		return mapping;
	}
	
	@GetMapping("/{id}")
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
	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = service.deletebyId(id);
		if(user == null) {
			throw new UserNotFoundException("id-"+ id);
		}
	}
	
	@GetMapping("/Report/{type}")
	public String generateReport(@PathVariable String type) throws FileNotFoundException, JRException {
		return reportService.generateReport(type);
	}
}
