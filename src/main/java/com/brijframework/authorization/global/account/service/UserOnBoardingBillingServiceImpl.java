package com.brijframework.authorization.global.account.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingBilling;
import com.brijframework.authorization.account.model.onboarding.UIUserOnBoardingBilling;
import com.brijframework.authorization.account.repository.UserAccountRepository;
import com.brijframework.authorization.account.repository.UserOnBoardingBillingRepository;
import com.brijframework.authorization.global.account.mapper.GlobalUserDetailMapper;
import com.brijframework.authorization.global.account.mapper.GlobalUserOnBoardingBillingMapper;

@Service
public class UserOnBoardingBillingServiceImpl extends CrudServiceImpl<UIUserOnBoardingBilling, EOUserOnBoardingBilling, Long> implements UserOnBoardingBillingService {

	@Autowired
	private UserOnBoardingBillingRepository userOnBoardingBillingRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	private GlobalUserDetailMapper userDetailMapper;
	
	@Autowired
	private GlobalUserOnBoardingBillingMapper userOnBoardingBillingMapper; 

	@Override
	public JpaRepository<EOUserOnBoardingBilling, Long> getRepository() {
		return userOnBoardingBillingRepository;
	}

	@Override
	public GenericMapper<EOUserOnBoardingBilling, UIUserOnBoardingBilling> getMapper() {
		return userOnBoardingBillingMapper;
	}

	@Override
	public UIUserOnBoardingBilling saveOnBoardingBilling(UIUserOnBoardingBilling userOnBoardingBilling, String username) {
		Optional<EOUserAccount> findUserAccount = userAccountRepository.findByUsername(username);
		if (!findUserAccount.isPresent()) {
			return null;
		}
		EOUserAccount eoUserAccount = findUserAccount.get();
		EOUserOnBoardingBilling eoUserOnBoardingBilling = userDetailMapper.mapToUserOnBoardingBillingDAO(userOnBoardingBilling);
		eoUserOnBoardingBilling.setUserAccount(eoUserAccount);
		eoUserOnBoardingBilling = userOnBoardingBillingRepository.saveAndFlush(eoUserOnBoardingBilling);
		return userDetailMapper.mapToUserOnBoardingBillingDTO(eoUserOnBoardingBilling);
	}

	@Override
	public List<UIUserOnBoardingBilling> findAllByUserAccountId(Long userAccountId) {
		return postFetch(userOnBoardingBillingRepository.findAllByUserAccountId(userAccountId));
	}

	@Override
	public List<UIUserOnBoardingBilling> postFetch(List<EOUserOnBoardingBilling> findObjects) {
		List<UIUserOnBoardingBilling> boardingQuestions = super.postFetch(findObjects);
		boardingQuestions.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return boardingQuestions;
	}

	@Override
	public void postFetch(EOUserOnBoardingBilling findObject, UIUserOnBoardingBilling dtoObject) {
	}

}
