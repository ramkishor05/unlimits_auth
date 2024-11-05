package com.brijframework.authorization.global.account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.authorization.account.model.UIGlobalDashboard;
import com.brijframework.authorization.account.repository.UserAccountRepository;
import com.brijframework.authorization.constant.Authority;
import com.brijframework.authorization.constant.RecordStatus;

@Service
public class GlobalDashboardServiceImpl implements GlobalDashboardService {

	private static final Logger LOGGER= LoggerFactory.getLogger(GlobalDashboardServiceImpl.class);

	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Override
	public UIGlobalDashboard getDashboard() {
		LOGGER.warn("Start geting the data for dashboard");
		UIGlobalDashboard clientDashboard=new UIGlobalDashboard();
		clientDashboard.setTotalUsers(userAccountRepository.countByUserRoleRoleNameInAndRecordStateIn(Authority.USER.getRoleNames() , RecordStatus.ACTIVETED.getStatusIds()));
		LOGGER.warn("End geting the data for dashboard");
		return clientDashboard;
	}

}
