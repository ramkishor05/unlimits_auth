package com.brijframework.authorization.device.account.model;

import com.brijframework.authorization.constant.ServiceType;

public class DeviceAuth {

	private ServiceType serviceType;

	public ServiceType getServiceType() {
		if(serviceType==null) {
			serviceType=ServiceType.NORMAL;
		}
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

}
