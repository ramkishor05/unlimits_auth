package com.brijframwork.authorization.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.brijframwork.authorization.beans.PasswordReset;
import com.brijframwork.authorization.beans.UIUserAccount;
import com.brijframwork.authorization.beans.UIUserProfile;
import com.brijframwork.authorization.beans.UserDetailResponse;

public interface UserAccountService extends UserDetailsService {

	boolean register(UIUserAccount userDetailRequest);

	boolean isAlreadyExists(String username);

	UIUserProfile updateUserProfile(UIUserProfile uiUserProfile);

	UserDetailResponse updateUserAccount(UIUserAccount uiUserAccount);

	UIUserProfile getUserProfile(Long id);

	List<UserDetailResponse> getUsers();

	boolean updateOnboarding(Long id, boolean onboarding, String idenNo);
	
	@Override
	UIUserAccount loadUserByUsername(String username) throws UsernameNotFoundException;

	UIUserAccount resetPassword(PasswordReset passwordReset);

	UIUserAccount saveOtp(PasswordReset passwordReset);
}
