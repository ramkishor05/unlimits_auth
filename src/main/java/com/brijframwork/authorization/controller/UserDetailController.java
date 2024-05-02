package com.brijframwork.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframwork.authorization.beans.UIUserAccount;
import com.brijframwork.authorization.beans.UIUserProfile;
import com.brijframwork.authorization.constant.Authority;
import com.brijframwork.authorization.service.UserAccountService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/user/detail")
@Hidden
public class UserDetailController {
	
	@Autowired
    private UserAccountService userDetailService;
	
	@PostMapping
	public ResponseEntity<?> register(@RequestBody UIUserAccount uiUserAccount){
    	return ResponseEntity.ok(userDetailService.register(uiUserAccount ,Authority.USER));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUserAccount(@PathVariable Long id, @RequestBody UIUserAccount uiUserAccount){
    	return ResponseEntity.ok(userDetailService.updateUserAccount(uiUserAccount));
	}
	
	@GetMapping
	public ResponseEntity<?> getUsers(){
    	return ResponseEntity.ok(userDetailService.getUsers());
	}
	
	@GetMapping("/exists/{username}")
	public ResponseEntity<?> isAlreadyExists(@PathVariable String username){
    	return ResponseEntity.ok(userDetailService.isAlreadyExists(username));
	}
	
	@PutMapping("/profile")
	public ResponseEntity<?> updateUserProfile(@RequestBody UIUserProfile uiUserProfile){
    	return ResponseEntity.ok(userDetailService.updateUserProfile(uiUserProfile));
	}
	
	@PutMapping("/onboarding/{id}/{onboarding}/{idenNo}")
	public ResponseEntity<?> updateOnboarding(@PathVariable Long id, @PathVariable Boolean onboarding,@PathVariable String idenNo){
    	return ResponseEntity.ok(userDetailService.updateOnboarding(id, onboarding, idenNo));
	}
	
	@GetMapping("/profile/{id}")
	public ResponseEntity<?> getUserProfile(@PathVariable Long id){
    	return ResponseEntity.ok(userDetailService.getUserProfile(id));
	}
	
}
