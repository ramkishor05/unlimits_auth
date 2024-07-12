package com.brijframework.authorization.global.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.account.entities.menus.EOUserRoleMenuGroup;
import com.brijframework.authorization.account.model.menus.UIUserRoleMenuGroup;
import com.brijframework.authorization.global.account.service.UserRoleMenuGroupService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/global/user/role/menu/group")
@CrossOrigin("*")
@Hidden
public class GlobalUserRoleMenuGroupController implements CrudController<UIUserRoleMenuGroup, EOUserRoleMenuGroup, Long>{
	
	@Autowired
    private UserRoleMenuGroupService roleMenuGroupService;

	@Override
	public CrudService<UIUserRoleMenuGroup, EOUserRoleMenuGroup, Long> getService() {
		return roleMenuGroupService;
	}

}
