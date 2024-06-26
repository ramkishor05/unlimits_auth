package com.brijframework.authorization.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.account.entities.menus.EOUserRoleMenuItem;
import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingMenu;

@Repository
@Transactional
public interface UserOnBoardingRepository  extends JpaRepository<EOUserOnBoardingMenu, Long>{

	void deleteByRoleMenuItem(EOUserRoleMenuItem eoRoleMenuItem);

}
