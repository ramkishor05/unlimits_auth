package com.brijframework.authorization.account.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingBilling;

@Repository
@Transactional
public interface UserOnBoardingBillingRepository  extends CustomRepository<EOUserOnBoardingBilling, Long>{

	List<EOUserOnBoardingBilling> findAllByUserAccount(EOUserAccount eoUserAccount);

	/**
	 * @param userAccountId
	 * @return
	 */
	List<EOUserOnBoardingBilling> findAllByUserAccountId(Long userAccountId);

}
