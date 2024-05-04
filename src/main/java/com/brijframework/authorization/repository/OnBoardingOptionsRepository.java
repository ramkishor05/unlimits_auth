package com.brijframework.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.model.onboarding.EOOnBoardingOptions;

@Repository
@Transactional
public interface OnBoardingOptionsRepository  extends JpaRepository<EOOnBoardingOptions, Long>{

}
