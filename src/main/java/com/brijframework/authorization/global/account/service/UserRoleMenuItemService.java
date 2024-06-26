package com.brijframework.authorization.global.account.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.account.entities.menus.EOUserRoleMenuItem;
import com.brijframework.authorization.account.model.menus.UIUserRoleMenuItem;

public interface UserRoleMenuItemService extends CrudService<UIUserRoleMenuItem, EOUserRoleMenuItem, Long>{

	List<UIUserRoleMenuItem> getRoleMenuItemList(String type);

}
