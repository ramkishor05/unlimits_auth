package com.brijframwork.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframwork.authorization.model.EOUserProfile;

@Repository
@Transactional
public interface UserProfileRepository extends JpaRepository<EOUserProfile, Long>{

}
