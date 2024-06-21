package com.brijframework.authorization.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.global.beans.UIMenuItem;
import com.brijframework.authorization.model.menus.EOMenuItem;

public interface MenuItemService extends CrudService<UIMenuItem, EOMenuItem, Long>{

	List<UIMenuItem> getMenuItemList(String type);

}
