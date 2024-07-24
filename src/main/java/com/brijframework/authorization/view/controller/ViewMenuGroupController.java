package com.brijframework.authorization.view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.view.entities.menus.EOViewMenuGroup;
import com.brijframework.authorization.view.model.menus.UIViewMenuGroup;
import com.brijframework.authorization.view.service.ViewMenuGroupService;

@RestController
@RequestMapping("/api/global/menu/group")
public class ViewMenuGroupController implements CrudController<UIViewMenuGroup, EOViewMenuGroup, Long>{
	
	@Autowired
    private ViewMenuGroupService menuGroupService;
	
	@GetMapping("/role/{roleId}")
	public ResponseEntity<List<UIViewMenuGroup>> getMenuGroupListByRole(@PathVariable Long roleId){
    	return ResponseEntity.ok(menuGroupService.getMenuGroupListByRole(roleId));
	}

	@Override
	public CrudService<UIViewMenuGroup, EOViewMenuGroup, Long> getService() {
		return menuGroupService;
	}
}
