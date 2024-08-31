package com.brijframework.authorization.global.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.account.entities.EOUserRole;
import com.brijframework.authorization.account.model.UIUserRole;
import com.brijframework.authorization.account.service.UserRoleService;

@RestController
@RequestMapping("/api/global/user/role")
@CrossOrigin("*")
public class GlobalUserRoleController implements CrudController<UIUserRole, EOUserRole, Long>{
	
	@Autowired
    private UserRoleService userRolelService;

	@Override
	public CrudService<UIUserRole, EOUserRole, Long> getService() {
		return userRolelService;
	}
	
}
