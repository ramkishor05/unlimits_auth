package com.brijframework.authorization.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.beans.UIMenuGroup;
import com.brijframework.authorization.model.menus.EOMenuGroup;
import com.brijframework.authorization.service.MenuGroupService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/menu/group")
@CrossOrigin("*")
@Hidden
public class MenuGroupController extends CrudController<UIMenuGroup, EOMenuGroup, Long>{
	
	@Autowired
    private MenuGroupService menuGroupService;
	
	@GetMapping("/role/{roleId}")
	public ResponseEntity<List<UIMenuGroup>> getMenuGroupListByRole(@PathVariable Long roleId){
    	return ResponseEntity.ok(menuGroupService.getMenuGroupListByRole(roleId));
	}

	@Override
	public CrudService<UIMenuGroup, EOMenuGroup, Long> getService() {
		return menuGroupService;
	}
}
