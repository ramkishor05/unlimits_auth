package com.brijframework.authorization.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/onboarding")
@CrossOrigin("*")
public class HomeController {

	@PutMapping("/question")
	public String updateOnBoardQuesrion(){
    	return "Welcome";
	}
}
