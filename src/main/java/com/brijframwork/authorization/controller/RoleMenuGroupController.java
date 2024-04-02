package com.brijframwork.authorization.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframwork.authorization.beans.UIRoleMenuGroup;
import com.brijframwork.authorization.service.RoleMenuGroupService;

@RestController
@RequestMapping("/api/role/menu/group")
@CrossOrigin("*")
public class RoleMenuGroupController {
	
	@Autowired
    private RoleMenuGroupService roleMenuGroupService;
	
	@PostMapping
	public ResponseEntity<UIRoleMenuGroup> addRoleMenuGroup(@RequestBody UIRoleMenuGroup uiRoleMenuGroup){
    	return ResponseEntity.ok(roleMenuGroupService.addRoleMenuGroup(uiRoleMenuGroup));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UIRoleMenuGroup> updateRoleMenuGroup(@PathVariable Long id, @RequestBody UIRoleMenuGroup uiRoleMenuGroup){
    	return ResponseEntity.ok(roleMenuGroupService.updateRoleMenuGroup(uiRoleMenuGroup));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteRoleMenuGroup(@PathVariable Long id){
    	return ResponseEntity.ok(roleMenuGroupService.deleteRoleMenuGroup(id));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UIRoleMenuGroup> getRoleMenuGroup(@PathVariable Long id){
    	return ResponseEntity.ok(roleMenuGroupService.getRoleMenuGroup(id));
	}
	
	@GetMapping
	public ResponseEntity<List<UIRoleMenuGroup>> getRoleMenuGroupList(){
    	return ResponseEntity.ok(roleMenuGroupService.getRoleMenuGroupList());
	}
}
