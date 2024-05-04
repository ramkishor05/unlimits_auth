package com.brijframework.authorization.controller;

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

import com.brijframework.authorization.beans.UIMenuItem;
import com.brijframework.authorization.service.MenuItemService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/menu/item")
@CrossOrigin("*")
@Hidden
public class MenuItemController {
	
	@Autowired
    private MenuItemService menuItemService;
	
	@PostMapping
	public ResponseEntity<UIMenuItem> addMenuItem(@RequestBody UIMenuItem uiMenuItem){
    	return ResponseEntity.ok(menuItemService.addMenuItem(uiMenuItem));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UIMenuItem> updateMenuItem(@PathVariable Long id, @RequestBody UIMenuItem uiMenuItem){
    	return ResponseEntity.ok(menuItemService.updateMenuItem(uiMenuItem));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteMenuItem(@PathVariable Long id){
    	return ResponseEntity.ok(menuItemService.deleteMenuItem(id));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UIMenuItem> getMenuItem(@PathVariable Long id){
    	return ResponseEntity.ok(menuItemService.getMenuItem(id));
	}
	
	@GetMapping
	public ResponseEntity<List<UIMenuItem>> getMenuItemList(){
    	return ResponseEntity.ok(menuItemService.getMenuItemList());
	}
	
}
