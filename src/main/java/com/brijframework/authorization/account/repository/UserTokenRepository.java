package com.brijframework.authorization.account.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.account.entities.EOUserToken;

@Repository
@Transactional
public interface UserTokenRepository  extends JpaRepository<EOUserToken, Long>{

	Optional<EOUserToken> findBySource(String token);
	
	Optional<EOUserToken> findByTarget(String token);

}
