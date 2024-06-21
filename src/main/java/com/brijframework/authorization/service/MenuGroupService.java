package com.brijframework.authorization.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.global.beans.UIMenuGroup;
import com.brijframework.authorization.model.menus.EOMenuGroup;

public interface MenuGroupService extends CrudService<UIMenuGroup, EOMenuGroup, Long>{

	List<UIMenuGroup> getMenuGroupListByRole(Long roleId);
}
