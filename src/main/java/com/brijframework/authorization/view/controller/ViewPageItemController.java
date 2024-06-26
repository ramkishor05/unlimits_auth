package com.brijframework.authorization.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.view.entities.pages.EOViewPageItem;
import com.brijframework.authorization.view.model.pages.UIViewPageItem;
import com.brijframework.authorization.view.service.ViewPageItemService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/global/page/item")
@CrossOrigin("*")
@Hidden
public class ViewPageItemController extends CrudController<UIViewPageItem, EOViewPageItem, Long>{
	
	@Autowired
    private ViewPageItemService pageItemService;

	@Override
	public CrudService<UIViewPageItem, EOViewPageItem, Long> getService() {
		return pageItemService;
	}
	
}
