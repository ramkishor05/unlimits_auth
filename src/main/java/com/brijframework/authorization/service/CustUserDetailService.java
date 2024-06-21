package com.brijframework.authorization.service;

import java.util.List;

import com.brijframework.authorization.global.beans.UserDetailRequest;
import com.brijframework.authorization.global.beans.UserDetailResponse;

public interface CustUserDetailService {

	UserDetailResponse registerAccount(Long ownerId, UserDetailRequest uiUserAccount);

	UserDetailResponse updateAccount(Long ownerId, UserDetailRequest uiUserAccount);

	List<UserDetailResponse> getCustUsers(Long ownerId);

	UserDetailResponse deleteAccount(Long ownerId, String username);
}
