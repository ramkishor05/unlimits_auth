package com.brijframework.authorization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.authorization.beans.UIMenuItem;
import com.brijframework.authorization.mapper.MenuItemMapper;
import com.brijframework.authorization.model.menus.EOMenuItem;
import com.brijframework.authorization.repository.MenuItemRepository;

@Service
public class MenuItemServiceImpl extends CrudServiceImpl<UIMenuItem, EOMenuItem, Long> implements MenuItemService {
	
	@Autowired
	private MenuItemRepository menuItemRepository;
	
	@Autowired
	private MenuItemMapper menuItemMapper;

	@Override
	public List<UIMenuItem> getMenuItemList(String type) {
		return menuItemMapper.mapToDTO(menuItemRepository.findAllByType(type));
	}

	@Override
	public JpaRepository<EOMenuItem, Long> getRepository() {
		return menuItemRepository;
	}

	@Override
	public GenericMapper<EOMenuItem, UIMenuItem> getMapper() {
		return menuItemMapper;
	}

}
