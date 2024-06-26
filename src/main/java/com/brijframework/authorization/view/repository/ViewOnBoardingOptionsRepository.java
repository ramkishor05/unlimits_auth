package com.brijframework.authorization.view.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.authorization.view.entities.onboarding.EOViewOnBoardingOptions;

@Repository
@Transactional
public interface ViewOnBoardingOptionsRepository  extends JpaRepository<EOViewOnBoardingOptions, Long>{

}
