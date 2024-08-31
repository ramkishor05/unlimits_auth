package com.brijframework.authorization.global.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.account.entities.menus.EOUserRoleMenuItem;
import com.brijframework.authorization.account.model.menus.UIUserRoleMenuItem;
import com.brijframework.authorization.global.account.service.UserRoleMenuItemService;

@RestController
@RequestMapping("/api/global/user/role/menu/item")
public class GlobalUserRoleMenuItemController implements CrudController<UIUserRoleMenuItem, EOUserRoleMenuItem, Long>{
	
	@Autowired
    private UserRoleMenuItemService roleMenuItemService;
	
	@GetMapping("/type/{type}")
	public ResponseEntity<List<UIUserRoleMenuItem>> getRoleMenuItemList(@PathVariable String type){
    	return ResponseEntity.ok(roleMenuItemService.getRoleMenuItemList(type));
	}

	@Override
	public CrudService<UIUserRoleMenuItem, EOUserRoleMenuItem, Long> getService() {
		return roleMenuItemService;
	}
	
}
