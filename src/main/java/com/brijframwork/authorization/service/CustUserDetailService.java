package com.brijframwork.authorization.service;

import java.util.List;

import com.brijframwork.authorization.beans.UserDetailRequest;
import com.brijframwork.authorization.beans.UserDetailResponse;

public interface CustUserDetailService {

	UserDetailResponse registerAccount(Long ownerId, UserDetailRequest uiUserAccount);

	UserDetailResponse updateAccount(Long ownerId, UserDetailRequest uiUserAccount);

	List<UserDetailResponse> getCustUsers(Long ownerId);

	UserDetailResponse deleteAccount(Long ownerId, String username);
}
