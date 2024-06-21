package com.brijframework.authorization.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.global.beans.UIUserRole;
import com.brijframework.authorization.model.EOUserRole;

public interface UserRoleService extends CrudService<UIUserRole, EOUserRole, Long>{

	List<UIUserRole> getUserRoleList(String type);

	List<UIUserRole> getUserRoleListByPositions(List<Integer> positions);

}
