package com.brijframework.authorization.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.account.entities.EOUserAccountService;

@Repository
@Transactional
public interface UserAccountServiceRepository  extends JpaRepository<EOUserAccountService, Long>{

	List<EOUserAccountService> findByUserAccountId(Long userAccountId);

}
