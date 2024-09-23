package com.brijframework.authorization.constant;

import java.util.Arrays;
import java.util.List;

public enum RecordStatus {
  
	ACTIVETED("ACTIVETED", Arrays.asList("ACTIVETED")), DACTIVETED("DACTIVETED",Arrays.asList("DACTIVETED")), ALL("All", Arrays.asList("ACTIVETED","DACTIVETED"));
	
	String status;
	List<String> statusIds;

	RecordStatus(String status, List<String> statusIds) {
		this.status=status;
		this.statusIds=statusIds;
	}

	public String getStatus() {
		return status;
	}

	public List<String> getStatusIds() {
		return statusIds;
	}
	
}
