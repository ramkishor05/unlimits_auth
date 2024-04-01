package com.brijframwork.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframwork.authorization.model.EOUserToken;

@Repository
@Transactional
public interface UserTokenRepository  extends JpaRepository<EOUserToken, Long>{

}
