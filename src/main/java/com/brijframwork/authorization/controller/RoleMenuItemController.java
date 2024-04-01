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

import com.brijframwork.authorization.beans.UIRoleMenuItem;
import com.brijframwork.authorization.service.RoleMenuItemService;

@RestController
@RequestMapping("/api/role/menu/item")
public class RoleMenuItemController {
	
	@Autowired
    private RoleMenuItemService roleMenuItemService;
	
	@PostMapping
	public ResponseEntity<UIRoleMenuItem> addRoleMenuItem(@RequestBody UIRoleMenuItem uiRoleMenuItem){
    	return ResponseEntity.ok(roleMenuItemService.addRoleMenuItem(uiRoleMenuItem));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UIRoleMenuItem> updateRoleMenuItem(@PathVariable Long id, @RequestBody UIRoleMenuItem uiRoleMenuItem){
    	return ResponseEntity.ok(roleMenuItemService.updateRoleMenuItem(uiRoleMenuItem));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteRoleMenuItem(@PathVariable Long id){
    	return ResponseEntity.ok(roleMenuItemService.deleteRoleMenuItem(id));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UIRoleMenuItem> getRoleMenuItem(@PathVariable Long id){
    	return ResponseEntity.ok(roleMenuItemService.getRoleMenuItem(id));
	}
	
	@GetMapping
	public ResponseEntity<List<UIRoleMenuItem>> getRoleMenuItemList(){
    	return ResponseEntity.ok(roleMenuItemService.getRoleMenuItemList());
	}
	
	@GetMapping("/type/{type}")
	public ResponseEntity<List<UIRoleMenuItem>> getRoleMenuItemList(@PathVariable String type){
    	return ResponseEntity.ok(roleMenuItemService.getRoleMenuItemList(type));
	}
	
}
