package com.brijframework.authorization.view.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.view.entities.menus.EOViewMenuItem;
import com.brijframework.authorization.view.model.menus.UIViewMenuItem;

public interface ViewMenuItemService extends CrudService<UIViewMenuItem, EOViewMenuItem, Long>{

	List<UIViewMenuItem> getMenuItemList(String type);

}
