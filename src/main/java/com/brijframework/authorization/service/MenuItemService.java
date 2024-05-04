package com.brijframework.authorization.service;

import java.util.List;

import com.brijframework.authorization.beans.UIMenuItem;

public interface MenuItemService {

	UIMenuItem addMenuItem(UIMenuItem uiMenuItem);

	UIMenuItem updateMenuItem(UIMenuItem uiMenuItem);

	boolean deleteMenuItem(Long id);

	UIMenuItem getMenuItem(Long id);

	List<UIMenuItem> getMenuItemList();

	List<UIMenuItem> getMenuItemList(String type);

}
