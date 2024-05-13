package com.brijframework.authorization.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.beans.UIRoleMenuItem;
import com.brijframework.authorization.model.menus.EORoleMenuItem;

public interface RoleMenuItemService extends CrudService<UIRoleMenuItem, EORoleMenuItem, Long>{

	List<UIRoleMenuItem> getRoleMenuItemList(String type);

}
