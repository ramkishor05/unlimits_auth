package com.brijframwork.authorization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframwork.authorization.beans.UIMenuItem;
import com.brijframwork.authorization.mapper.MenuItemMapper;
import com.brijframwork.authorization.model.menus.EOMenuItem;
import com.brijframwork.authorization.repository.MenuItemRepository;

@Service
public class MenuItemServiceImpl implements MenuItemService {
	
	@Autowired
	private MenuItemRepository menuItemRepository;
	
	@Autowired
	private MenuItemMapper menuItemMapper;

	@Override
	public UIMenuItem addMenuItem(UIMenuItem uiMenuItem) {
		EOMenuItem eoMenuItem = menuItemMapper.mapToDAO(uiMenuItem);
		eoMenuItem=menuItemRepository.save(eoMenuItem);
		
		return menuItemMapper.mapToDTO(eoMenuItem);
	}

	@Override
	public UIMenuItem updateMenuItem(UIMenuItem uiMenuItem) {
		EOMenuItem eoMenuItem = menuItemMapper.mapToDAO(uiMenuItem);
		eoMenuItem=menuItemRepository.save(eoMenuItem);
		return menuItemMapper.mapToDTO(eoMenuItem);
	}

	@Override
	public boolean deleteMenuItem(Long id) {
		menuItemRepository.deleteById(id);
		return true;
	}

	@Override
	public UIMenuItem getMenuItem(Long id) {
		return menuItemMapper.mapToDTO(menuItemRepository.findById(id).orElse(null));
	}

	@Override
	public List<UIMenuItem> getMenuItemList() {
		return menuItemMapper.mapToDTO(menuItemRepository.findAll());
	}

	@Override
	public List<UIMenuItem> getMenuItemList(String type) {
		return menuItemMapper.mapToDTO(menuItemRepository.findAllByType(type));
	}

}
