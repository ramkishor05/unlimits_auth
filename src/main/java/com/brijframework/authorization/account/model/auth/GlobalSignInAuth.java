package com.brijframework.authorization.account.model.auth;

import com.brijframework.authorization.constant.ServiceType;
import com.brijframework.authorization.constant.UserType;

public class GlobalSignInAuth {

	private ServiceType serviceType;
	private UserType userType;


	public ServiceType getServiceType() {
		if(serviceType==null) {
			serviceType=ServiceType.NORMAL;
		}
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public UserType getUserType() {
		if(userType==null) {
			userType=UserType.APP;
		}
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
}
