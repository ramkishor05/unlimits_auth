package com.brijframwork.authorization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframwork.authorization.beans.UIRoleMenuGroup;
import com.brijframwork.authorization.mapper.RoleMenuGroupMapper;
import com.brijframwork.authorization.model.menus.EORoleMenuGroup;
import com.brijframwork.authorization.repository.RoleMenuGroupRepository;

@Service
public class RoleMenuGroupServiceImpl implements RoleMenuGroupService {
	
	@Autowired
	private RoleMenuGroupRepository roleMenuGroupRepository;
	
	@Autowired
	private RoleMenuGroupMapper roleMenuGroupMapper;

	@Override
	public UIRoleMenuGroup addRoleMenuGroup(UIRoleMenuGroup uiRoleMenuGroup) {
		EORoleMenuGroup eoRoleMenuGroup = roleMenuGroupMapper.mapToDAO(uiRoleMenuGroup);
		eoRoleMenuGroup=roleMenuGroupRepository.save(eoRoleMenuGroup);
		
		return roleMenuGroupMapper.mapToDTO(eoRoleMenuGroup);
	}

	@Override
	public UIRoleMenuGroup updateRoleMenuGroup(UIRoleMenuGroup uiRoleMenuGroup) {
		EORoleMenuGroup eoRoleMenuGroup = roleMenuGroupMapper.mapToDAO(uiRoleMenuGroup);
		eoRoleMenuGroup=roleMenuGroupRepository.save(eoRoleMenuGroup);
		return roleMenuGroupMapper.mapToDTO(eoRoleMenuGroup);
	}

	@Override
	public boolean deleteRoleMenuGroup(Long id) {
		roleMenuGroupRepository.deleteById(id);
		return true;
	}

	@Override
	public UIRoleMenuGroup getRoleMenuGroup(Long id) {
		return roleMenuGroupMapper.mapToDTO(roleMenuGroupRepository.findById(id).orElse(null));
	}

	@Override
	public List<UIRoleMenuGroup> getRoleMenuGroupList() {
		return roleMenuGroupMapper.mapToDTO(roleMenuGroupRepository.findAll());
	}

}
