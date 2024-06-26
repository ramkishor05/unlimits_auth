package com.brijframework.authorization.global.account.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.entities.menus.EOUserRoleMenuItem;
import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingMenu;
import com.brijframework.authorization.account.repository.UserAccountRepository;
import com.brijframework.authorization.account.repository.UserOnBoardingRepository;

@Service
public class UserOnBoardingServiceImpl implements UserOnBoardingService {

	@Autowired
	private UserOnBoardingRepository userOnBoardingRepository;
	
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	private UserOnBoardingQuestionService userOnBoardingQuestionService;

	@Override
	public void initOnBoarding(EOUserAccount eoUserAccount) {
		Map<String, EOUserOnBoardingMenu> onBoardingMap = Optional.ofNullable(eoUserAccount.getOnBoardingMenuList()).orElse(new ArrayList<EOUserOnBoardingMenu>()).parallelStream().collect(Collectors.toMap((eoUserOnBoarding)->getOnBoardingKey(eoUserOnBoarding.getRoleMenuItem(), eoUserOnBoarding.getUserAccount()), Function.identity()));
		List<EOUserRoleMenuItem> roleMenuItems = eoUserAccount.getUserRole().getRoleMenuItems();
		for(EOUserRoleMenuItem eoRoleMenuItem:  roleMenuItems) {
			String onBoardingKey = getOnBoardingKey(eoRoleMenuItem, eoUserAccount);
			if(eoRoleMenuItem.getOnBoarding()) {
				EOUserOnBoardingMenu eoUserOnBoarding=onBoardingMap.getOrDefault(onBoardingKey, new EOUserOnBoardingMenu()) ;
				eoUserOnBoarding.setUserAccount(eoUserAccount);
				eoUserOnBoarding.setRoleMenuItem(eoRoleMenuItem);
				eoUserOnBoarding.setOnBoardingLevel(eoRoleMenuItem.getOnBoardingLevel());
				userOnBoardingRepository.save(eoUserOnBoarding);
			}
		}
		if(!org.springframework.util.CollectionUtils.isEmpty(eoUserAccount.getOnBoardingMenuList())) {
			if(eoUserAccount.getOnBoardingMenuList().isEmpty()) {
				eoUserAccount.setOnBoarding(!eoUserAccount.getOnBoardingMenuList().isEmpty());
				userAccountRepository.save(eoUserAccount);
			} else {
				eoUserAccount.setOnBoarding(!eoUserAccount.getOnBoardingMenuList().stream().allMatch(onBoarding->onBoarding.getOnBoardingStatus()));
				userAccountRepository.save(eoUserAccount);
			}
		}
		userOnBoardingQuestionService.initOnBoarding(eoUserAccount);
	}

	private String getOnBoardingKey(EOUserRoleMenuItem roleMenuItem, EOUserAccount userAccount) {
		return roleMenuItem.getId()+"_"+userAccount.getId();
	}

	@Override
	public void saveOnBoardingStatus(boolean onboarding, String idenNo, List<EOUserOnBoardingMenu> eoUserOnBoardingList) {
		for(EOUserOnBoardingMenu eoUserOnBoarding:  eoUserOnBoardingList) {
			if(eoUserOnBoarding.getRoleMenuItem()==null) {
				continue;
			}
			if(eoUserOnBoarding.getRoleMenuItem().getIdenNo()==null) {
				continue;
			}
			if(eoUserOnBoarding.getRoleMenuItem().getIdenNo().equals(idenNo)) {
				eoUserOnBoarding.setOnBoardingStatus(onboarding);
				userOnBoardingRepository.save(eoUserOnBoarding);
			}
		}
	}

	@Override
	public boolean updateOnBoardingStatus(boolean onboarding, String idenNo, EOUserAccount eoUserAccount) {
		List<EOUserOnBoardingMenu> onBoardingList = eoUserAccount.getOnBoardingMenuList();
		saveOnBoardingStatus(onboarding , idenNo, onBoardingList);
		eoUserAccount.setOnBoarding(onBoardingList.stream().anyMatch(onBoarding->onBoarding.getOnBoardingStatus().equals(false)));
		userAccountRepository.save(eoUserAccount);
		return true;
	}
}
