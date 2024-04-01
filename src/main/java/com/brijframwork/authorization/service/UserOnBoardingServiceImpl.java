package com.brijframwork.authorization.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframwork.authorization.model.EOUserAccount;
import com.brijframwork.authorization.model.menus.EORoleMenuItem;
import com.brijframwork.authorization.model.onboarding.EOUserOnBoarding;
import com.brijframwork.authorization.repository.UserAccountRepository;
import com.brijframwork.authorization.repository.UserOnBoardingRepository;

@Service
public class UserOnBoardingServiceImpl implements UserOnBoardingService {

	@Autowired
	private UserOnBoardingRepository userOnBoardingRepository;
	
	@Autowired
	private UserAccountRepository userAccountRepository;

	@Override
	public void initOnBoarding(EOUserAccount eoUserAccount) {
		if(eoUserAccount==null) {
			return;
		}
		Map<String, EOUserOnBoarding> onBoardingMap = Optional.ofNullable(eoUserAccount.getOnBoardingList()).orElse(new ArrayList<EOUserOnBoarding>()).parallelStream().collect(Collectors.toMap((eoUserOnBoarding)->getOnBoardingKey(eoUserOnBoarding.getRoleMenuItem(), eoUserOnBoarding.getUserAccount()), Function.identity()));
		List<EORoleMenuItem> roleMenuItems = eoUserAccount.getUserRole().getRoleMenuItems();
		for(EORoleMenuItem eoRoleMenuItem:  roleMenuItems) {
			String onBoardingKey = getOnBoardingKey(eoRoleMenuItem, eoUserAccount);
			if(eoRoleMenuItem.getOnBoarding()) {
				EOUserOnBoarding eoUserOnBoarding=onBoardingMap.getOrDefault(onBoardingKey, new EOUserOnBoarding()) ;
				eoUserOnBoarding.setUserAccount(eoUserAccount);
				eoUserOnBoarding.setRoleMenuItem(eoRoleMenuItem);
				eoUserOnBoarding.setOnBoardingLevel(eoRoleMenuItem.getOnBoardingLevel());
				userOnBoardingRepository.save(eoUserOnBoarding);
			}
		}
		if(!org.springframework.util.CollectionUtils.isEmpty(eoUserAccount.getOnBoardingList())) {
			if(eoUserAccount.getOnBoardingList().isEmpty()) {
				eoUserAccount.setOnBoarding(!eoUserAccount.getOnBoardingList().isEmpty());
				userAccountRepository.save(eoUserAccount);
			} else {
				eoUserAccount.setOnBoarding(!eoUserAccount.getOnBoardingList().stream().allMatch(onBoarding->onBoarding.getOnBoardingStatus()));
				userAccountRepository.save(eoUserAccount);
			}
		}
	}

	private String getOnBoardingKey(EORoleMenuItem roleMenuItem, EOUserAccount userAccount) {
		return roleMenuItem.getId()+"_"+userAccount.getId();
	}

	@Override
	public void saveOnBoardingStatus(boolean onboarding, String idenNo, List<EOUserOnBoarding> eoUserOnBoardingList) {
		for(EOUserOnBoarding eoUserOnBoarding:  eoUserOnBoardingList) {
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
		List<EOUserOnBoarding> onBoardingList = eoUserAccount.getOnBoardingList();
		saveOnBoardingStatus(onboarding , idenNo, onBoardingList);
		eoUserAccount.setOnBoarding(onBoardingList.stream().anyMatch(onBoarding->onBoarding.getOnBoardingStatus().equals(false)));
		userAccountRepository.save(eoUserAccount);
		return true;
	}
}
