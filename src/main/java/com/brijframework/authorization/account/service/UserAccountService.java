package com.brijframework.authorization.account.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.model.UIUserAccount;
import com.brijframework.authorization.account.model.UIUserProfile;
import com.brijframework.authorization.account.model.UserDetailResponse;
import com.brijframework.authorization.account.model.auth.GlobalAuthDataDTO;
import com.brijframework.authorization.account.model.auth.GlobalLoginRequest;
import com.brijframework.authorization.account.model.auth.GlobalPasswordReset;
import com.brijframework.authorization.account.model.auth.GlobalRegisterRequest;
import com.brijframework.authorization.device.account.model.DeviceLoginRequest;

public interface UserAccountService extends UserDetailsService, CrudService<UserDetailResponse, EOUserAccount, Long> {

	GlobalAuthDataDTO register(GlobalRegisterRequest registerRequest);
	
	GlobalAuthDataDTO login(GlobalLoginRequest loginRequest);
	
	Boolean isAlreadyExists(String username);

	UIUserProfile updateUserProfile(UIUserProfile uiUserProfile);
	
	UIUserProfile updateUserProfile(EOUserAccount eoUserAccount, UIUserProfile uiUserProfile);

	UserDetailResponse updateUserAccount(UIUserAccount uiUserAccount);

	UIUserProfile getUserProfile(Long id);

	List<UserDetailResponse> getUsers();

	boolean updateOnboarding(Long id, boolean onboarding, String idenNo);
	
	@Override
	UIUserAccount loadUserByUsername(String username) throws UsernameNotFoundException;

	UIUserAccount resetPassword(GlobalPasswordReset passwordReset);

	UIUserAccount saveOtp(GlobalPasswordReset passwordReset);

	/**
	 * @param authRequest
	 * @return
	 */
	Optional<EOUserAccount> find(GlobalLoginRequest authRequest);

	UIUserAccount getUserDetail(EOUserAccount eoUserAccount);

	UIUserProfile getUserProfile(EOUserAccount currentAccount);

	Boolean passwordUpdateByToken(EOUserAccount currentAccount, DeviceLoginRequest deviceLoginRequest);

	GlobalAuthDataDTO tryLogin(GlobalRegisterRequest loginRequest);

}