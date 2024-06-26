package com.brijframework.authorization.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingBilling;

@Repository
@Transactional
public interface UserOnBoardingBillingRepository  extends JpaRepository<EOUserOnBoardingBilling, Long>{

	List<EOUserOnBoardingBilling> findAllByUserAccount(EOUserAccount eoUserAccount);

	/**
	 * @param userAccountId
	 * @return
	 */
	List<EOUserOnBoardingBilling> findAllByUserAccountId(Long userAccountId);

}
