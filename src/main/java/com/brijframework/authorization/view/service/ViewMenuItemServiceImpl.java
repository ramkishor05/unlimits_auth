package com.brijframework.authorization.view.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.authorization.view.entities.menus.EOViewMenuItem;
import com.brijframework.authorization.view.mapper.ViewMenuItemMapper;
import com.brijframework.authorization.view.model.menus.UIViewMenuItem;
import com.brijframework.authorization.view.repository.ViewMenuItemRepository;

@Service
public class ViewMenuItemServiceImpl extends CrudServiceImpl<UIViewMenuItem, EOViewMenuItem, Long> implements ViewMenuItemService {
	
	@Autowired
	private ViewMenuItemRepository menuItemRepository;
	
	@Autowired
	private ViewMenuItemMapper menuItemMapper;

	@Override
	public List<UIViewMenuItem> getMenuItemList(String type) {
		return menuItemMapper.mapToDTO(menuItemRepository.findAllByType(type));
	}

	@Override
	public JpaRepository<EOViewMenuItem, Long> getRepository() {
		return menuItemRepository;
	}

	@Override
	public GenericMapper<EOViewMenuItem, UIViewMenuItem> getMapper() {
		return menuItemMapper;
	}

}
