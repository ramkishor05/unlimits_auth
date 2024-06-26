package com.brijframework.authorization.view.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.authorization.view.entities.pages.EOViewPageItem;
import com.brijframework.authorization.view.mapper.ViewPageItemMapper;
import com.brijframework.authorization.view.model.pages.UIViewPageItem;
import com.brijframework.authorization.view.repository.ViewPageItemRepository;

@Service
public class ViewPageItemServiceImpl extends CrudServiceImpl<UIViewPageItem, EOViewPageItem, Long> implements ViewPageItemService {
	
	@Autowired
	private ViewPageItemRepository pageItemRepository;
	
	@Autowired
	private ViewPageItemMapper pageItemMapper;

	@Override
	public List<UIViewPageItem> getPageItemList(String type) {
		return pageItemMapper.mapToDTO(pageItemRepository.findAllByType(type));
	}

	@Override
	public JpaRepository<EOViewPageItem, Long> getRepository() {
		return pageItemRepository;
	}

	@Override
	public GenericMapper<EOViewPageItem, UIViewPageItem> getMapper() {
		return pageItemMapper;
	}

}
