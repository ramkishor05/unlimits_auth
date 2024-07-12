package com.brijframework.authorization.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.view.entities.menus.EOViewMenuItem;
import com.brijframework.authorization.view.model.menus.UIViewMenuItem;
import com.brijframework.authorization.view.service.ViewMenuItemService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/global/menu/item")
@CrossOrigin("*")
@Hidden
public class ViewMenuItemController implements CrudController<UIViewMenuItem, EOViewMenuItem, Long>{
	
	@Autowired
    private ViewMenuItemService menuItemService;

	@Override
	public CrudService<UIViewMenuItem, EOViewMenuItem, Long> getService() {
		return menuItemService;
	}
	
}
