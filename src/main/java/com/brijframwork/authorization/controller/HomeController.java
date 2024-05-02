package com.brijframwork.authorization.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/home")
@PreAuthorize("hasAuthority('ADMIN')")
@Hidden
public class HomeController {

	@GetMapping
	public String getWelcome(){
    	return "Welcome";
	}
	
	@PostMapping
	public String putWelcome(){
    	return "Welcome";
	}
}
