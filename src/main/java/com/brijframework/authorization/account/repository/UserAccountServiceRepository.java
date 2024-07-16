package com.brijframework.authorization.account.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.authorization.account.entities.EOUserAccountService;

@Repository
@Transactional
public interface UserAccountServiceRepository  extends CustomRepository<EOUserAccountService, Long>{

	List<EOUserAccountService> findByUserAccountId(Long userAccountId);

}
