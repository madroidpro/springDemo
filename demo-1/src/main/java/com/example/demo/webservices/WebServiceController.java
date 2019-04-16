package com.example.demo.webservices;

import javax.validation.constraints.Pattern;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController	
public class WebServiceController {
	
	@GetMapping("/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping("/hello-world-bean/{msg}")
	public WebserviceBean helloBean(@PathVariable @Pattern(regexp = "[a-z]") String msg) {
		return new WebserviceBean(msg);
	}

}
