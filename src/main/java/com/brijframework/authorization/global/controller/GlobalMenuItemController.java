package com.brijframework.authorization.global.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.global.beans.UIMenuItem;
import com.brijframework.authorization.model.menus.EOMenuItem;
import com.brijframework.authorization.service.MenuItemService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/global/menu/item")
@CrossOrigin("*")
@Hidden
public class GlobalMenuItemController extends CrudController<UIMenuItem, EOMenuItem, Long>{
	
	@Autowired
    private MenuItemService menuItemService;

	@Override
	public CrudService<UIMenuItem, EOMenuItem, Long> getService() {
		return menuItemService;
	}
	
}
