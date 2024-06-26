package com.brijframework.authorization.view.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.authorization.account.entities.pages.EOUserRolePageGroup;
import com.brijframework.authorization.account.entities.pages.EOUserRolePageItem;
import com.brijframework.authorization.account.repository.UserRolePageGroupRepository;
import com.brijframework.authorization.view.entities.pages.EOViewPageGroup;
import com.brijframework.authorization.view.mapper.ViewPageGroupMapper;
import com.brijframework.authorization.view.mapper.ViewPageItemMapper;
import com.brijframework.authorization.view.model.pages.UIViewPageGroup;
import com.brijframework.authorization.view.model.pages.UIViewPageItem;
import com.brijframework.authorization.view.repository.ViewPageGroupRepository;

@Service
public class ViewPageGroupServiceImpl extends CrudServiceImpl<UIViewPageGroup, EOViewPageGroup, Long> implements ViewPageGroupService {
	
	@Autowired
	private UserRolePageGroupRepository userRolePageGroupRepository;
	
	@Autowired
	private ViewPageGroupRepository pageGroupRepository;
	
	@Autowired
	private ViewPageGroupMapper pageGroupMapper;
	
	@Autowired
	private ViewPageItemMapper pageItemMapper;
	
	@Override
	public JpaRepository<EOViewPageGroup, Long> getRepository() {
		return pageGroupRepository;
	}

	@Override
	public GenericMapper<EOViewPageGroup, UIViewPageGroup> getMapper() {
		return pageGroupMapper;
	}

	@Override
	public List<UIViewPageGroup> getPageGroupListByRole(Long roleId) {
		List<EOUserRolePageGroup> eoRolePageGroups = userRolePageGroupRepository.findAllByRoleId(roleId);
		List<UIViewPageGroup> uiPageGroups=new ArrayList<UIViewPageGroup>();
		for(EOUserRolePageGroup eoRolePageGroup: eoRolePageGroups) {
			UIViewPageGroup uiPageGroup = pageGroupMapper.mapToDTO(eoRolePageGroup.getPageGroup());
			for(EOUserRolePageItem eoRolePageItem: eoRolePageGroup.getRolePageItems()) {
				UIViewPageItem uiPageItem = pageItemMapper.mapToDTO(eoRolePageItem.getPageItem());
				uiPageItem.setHomePage(eoRolePageItem.isHomePage());
				uiPageGroup.getPageItems().add(uiPageItem);
			}
			uiPageGroup.getPageItems().sort((m1,m2)->m1.getOrderSequence().compareTo(m2.getOrderSequence()));
			uiPageGroups.add(uiPageGroup);
		}
		uiPageGroups.sort((m1,m2)->m1.getOrderSequence().compareTo(m2.getOrderSequence()));
		return uiPageGroups;
	}

	
}
