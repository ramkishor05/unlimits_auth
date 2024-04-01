package com.brijframwork.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframwork.authorization.model.menus.EORoleMenuItem;
import com.brijframwork.authorization.model.onboarding.EOUserOnBoarding;

@Repository
@Transactional
public interface UserOnBoardingRepository  extends JpaRepository<EOUserOnBoarding, Long>{

	void deleteByRoleMenuItem(EORoleMenuItem eoRoleMenuItem);

}
