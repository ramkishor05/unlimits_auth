package com.brijframework.authorization.global.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.global.beans.UIRoleMenuItem;
import com.brijframework.authorization.model.menus.EORoleMenuItem;
import com.brijframework.authorization.service.RoleMenuItemService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/global/role/menu/item")
@Hidden
public class GlobalRoleMenuItemController extends CrudController<UIRoleMenuItem, EORoleMenuItem, Long>{
	
	@Autowired
    private RoleMenuItemService roleMenuItemService;
	
	
	@GetMapping("/type/{type}")
	public ResponseEntity<List<UIRoleMenuItem>> getRoleMenuItemList(@PathVariable String type){
    	return ResponseEntity.ok(roleMenuItemService.getRoleMenuItemList(type));
	}

	@Override
	public CrudService<UIRoleMenuItem, EORoleMenuItem, Long> getService() {
		return roleMenuItemService;
	}
	
}
