package com.brijframework.authorization.account.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.account.entities.EOUserRole;
import com.brijframework.authorization.account.model.UIUserRole;

public interface UserRoleService extends CrudService<UIUserRole, EOUserRole, Long>{

	List<UIUserRole> getUserRoleList(String type);

	List<UIUserRole> getUserRoleListByPositions(List<Integer> positions);

}
