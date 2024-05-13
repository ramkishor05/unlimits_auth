package com.brijframework.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.beans.UIRoleMenuGroup;
import com.brijframework.authorization.model.menus.EORoleMenuGroup;
import com.brijframework.authorization.service.RoleMenuGroupService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/role/menu/group")
@CrossOrigin("*")
@Hidden
public class RoleMenuGroupController extends CrudController<UIRoleMenuGroup, EORoleMenuGroup, Long>{
	
	@Autowired
    private RoleMenuGroupService roleMenuGroupService;

	@Override
	public CrudService<UIRoleMenuGroup, EORoleMenuGroup, Long> getService() {
		return roleMenuGroupService;
	}

}
