package com.brijframework.authorization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.authorization.beans.UIUserRole;
import com.brijframework.authorization.mapper.UserRoleMapper;
import com.brijframework.authorization.model.EOUserRole;
import com.brijframework.authorization.repository.UserRoleRepository;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	public UIUserRole addUserRole(UIUserRole uiUserRole) {
		EOUserRole eoUserRole = userRoleMapper.mapToDAO(uiUserRole);
		eoUserRole=userRoleRepository.save(eoUserRole);
		return userRoleMapper.mapToDTO(eoUserRole);
	}

	@Override
	public UIUserRole updateUserRole(UIUserRole uiUserRole) {
		EOUserRole eoUserRole = userRoleMapper.mapToDAO(uiUserRole);
		eoUserRole=userRoleRepository.save(eoUserRole);
		return userRoleMapper.mapToDTO(eoUserRole);
	}

	@Override
	public Boolean deleteUserRole(Long id) {
		userRoleRepository.deleteById(id);
		return true;
	}

	@Override
	public UIUserRole getUserRole(Long id) {
		return userRoleMapper.mapToDTO(userRoleRepository.findById(id).orElse(null));
	}

	@Override
	public List<UIUserRole> getUserRoleList() {
		return userRoleMapper.mapToDTO(userRoleRepository.findAll());
	}

	@Override
	public List<UIUserRole> getUserRoleList(String type) {
		return userRoleMapper.mapToDTO(userRoleRepository.findAllByRoleType(type));
	}

}
