package com.brijframework.authorization.view.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.view.entities.menus.EOViewMenuGroup;
import com.brijframework.authorization.view.model.menus.UIViewMenuGroup;

public interface ViewMenuGroupService extends CrudService<UIViewMenuGroup, EOViewMenuGroup, Long>{

	List<UIViewMenuGroup> getMenuGroupListByRole(Long roleId);
}
