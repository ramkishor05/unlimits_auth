package com.brijframwork.authorization.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframwork.authorization.beans.UIUserRole;
import com.brijframwork.authorization.service.UserRoleService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/user/role")
@Hidden
public class UserRoleController {
	
	@Autowired
    private UserRoleService userRolelService;
	
	@PostMapping
	public ResponseEntity<UIUserRole> addUserRole(@RequestBody UIUserRole uiUserRole){
    	return ResponseEntity.ok(userRolelService.addUserRole(uiUserRole));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UIUserRole> updateUserRole(@PathVariable Long id, @RequestBody UIUserRole uiUserRole){
    	return ResponseEntity.ok(userRolelService.updateUserRole(uiUserRole));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteUserRole(@PathVariable Long id){
    	return ResponseEntity.ok(userRolelService.deleteUserRole(id));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UIUserRole> getUserRole(@PathVariable Long id){
    	return ResponseEntity.ok(userRolelService.getUserRole(id));
	}
	
	@GetMapping
	public ResponseEntity<List<UIUserRole>> getUserRoleList(){
    	return ResponseEntity.ok(userRolelService.getUserRoleList());
	}
	
	@GetMapping("/type/{type}")
	public ResponseEntity<List<UIUserRole>> getUserRoleList(@PathVariable String type){
    	return ResponseEntity.ok(userRolelService.getUserRoleList(type));
	}
	
}
