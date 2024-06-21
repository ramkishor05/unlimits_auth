package com.brijframework.authorization.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.authorization.global.beans.GlobalLoginRequest;
import com.brijframework.authorization.global.beans.GlobalPasswordReset;
import com.brijframework.authorization.global.beans.GlobalRegisterRequest;
import com.brijframework.authorization.global.beans.Response;
import com.brijframework.authorization.global.beans.UIUserAccount;
import com.brijframework.authorization.global.beans.UIUserProfile;
import com.brijframework.authorization.global.beans.UserDetailResponse;
import com.brijframework.authorization.model.EOUserAccount;

public interface UserAccountService extends UserDetailsService, QueryService<UserDetailResponse, EOUserAccount, Long> {

	Response register(GlobalRegisterRequest registerRequest);
	
	Response login(GlobalLoginRequest loginRequest);
	
	boolean isAlreadyExists(String username);

	UIUserProfile updateUserProfile(UIUserProfile uiUserProfile);

	UserDetailResponse updateUserAccount(UIUserAccount uiUserAccount);

	UIUserProfile getUserProfile(Long id);

	List<UserDetailResponse> getUsers();

	boolean updateOnboarding(Long id, boolean onboarding, String idenNo);
	
	@Override
	UIUserAccount loadUserByUsername(String username) throws UsernameNotFoundException;

	UIUserAccount resetPassword(GlobalPasswordReset passwordReset);

	UIUserAccount saveOtp(GlobalPasswordReset passwordReset);
}
