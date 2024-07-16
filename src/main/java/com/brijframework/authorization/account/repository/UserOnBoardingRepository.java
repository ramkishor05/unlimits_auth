package com.brijframework.authorization.account.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.authorization.account.entities.menus.EOUserRoleMenuItem;
import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingMenu;

@Repository
@Transactional
public interface UserOnBoardingRepository  extends CustomRepository<EOUserOnBoardingMenu, Long>{

	void deleteByRoleMenuItem(EOUserRoleMenuItem eoRoleMenuItem);

}
