package com.brijframework.authorization.cust.account.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.account.entities.EOUserRole;
import com.brijframework.authorization.account.model.UIUserRole;

public interface CustRoleService extends CrudService<UIUserRole, EOUserRole, Long>{

	List<UIUserRole> getUserRoleList(String type);


}
