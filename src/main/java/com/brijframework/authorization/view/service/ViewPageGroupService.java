package com.brijframework.authorization.view.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.view.entities.pages.EOViewPageGroup;
import com.brijframework.authorization.view.model.pages.UIViewPageGroup;

public interface ViewPageGroupService extends CrudService<UIViewPageGroup, EOViewPageGroup, Long>{

	List<UIViewPageGroup> getPageGroupListByRole(Long roleId);
}
