package com.brijframework.authorization.account.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.authorization.account.entities.EOUserProfile;

@Repository
@Transactional
public interface UserProfileRepository extends CustomRepository<EOUserProfile, Long>{

}
