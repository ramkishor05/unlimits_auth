package com.brijframework.authorization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.authorization.global.beans.UIUserRole;
import com.brijframework.authorization.mapper.UserRoleMapper;
import com.brijframework.authorization.model.EOUserRole;
import com.brijframework.authorization.repository.UserRoleRepository;

@Service
public class UserRoleServiceImpl extends CrudServiceImpl<UIUserRole, EOUserRole, Long> implements UserRoleService {
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	public List<UIUserRole> getUserRoleList(String type) {
		return userRoleMapper.mapToDTO(userRoleRepository.findAllByRoleType(type));
	}
	
	@Override
	public List<UIUserRole> getUserRoleListByPositions(List<Integer> positions) {
		return userRoleMapper.mapToDTO(userRoleRepository.findByPositions(positions));
	}

	@Override
	public JpaRepository<EOUserRole, Long> getRepository() {
		return userRoleRepository;
	}

	@Override
	public GenericMapper<EOUserRole, UIUserRole> getMapper() {
		return userRoleMapper;
	}

}
