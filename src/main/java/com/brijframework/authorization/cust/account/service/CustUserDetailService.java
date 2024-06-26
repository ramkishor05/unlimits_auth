package com.brijframework.authorization.cust.account.service;

import java.util.List;

import com.brijframework.authorization.account.model.UserDetailRequest;
import com.brijframework.authorization.account.model.UserDetailResponse;

public interface CustUserDetailService {

	UserDetailResponse registerAccount(Long ownerId, UserDetailRequest uiUserAccount);

	UserDetailResponse updateAccount(Long ownerId, UserDetailRequest uiUserAccount);

	List<UserDetailResponse> getCustUsers(Long ownerId);

	UserDetailResponse deleteAccount(Long ownerId, String username);
}
