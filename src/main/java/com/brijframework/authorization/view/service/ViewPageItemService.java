package com.brijframework.authorization.view.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.view.entities.pages.EOViewPageItem;
import com.brijframework.authorization.view.model.pages.UIViewPageItem;

public interface ViewPageItemService extends CrudService<UIViewPageItem, EOViewPageItem, Long>{

	List<UIViewPageItem> getPageItemList(String type);

}
