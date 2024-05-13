package com.brijframework.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.beans.UIUserRole;
import com.brijframework.authorization.model.EOUserRole;
import com.brijframework.authorization.service.UserRoleService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/user/role")
@Hidden
public class UserRoleController extends CrudController<UIUserRole, EOUserRole, Long>{
	
	@Autowired
    private UserRoleService userRolelService;

	@Override
	public CrudService<UIUserRole, EOUserRole, Long> getService() {
		return userRolelService;
	}
	
}
