/**
 * 
 */
package com.brijframework.authorization.context;

import com.brijframework.authorization.account.entities.EOUserAccount;

/**
 *  @author omnie
 */
public class ApiSecurityContext {
	
	private static ApiSecurityContext securityContext;
	
	private ThreadLocal<EOUserAccount> userAccountRequest=new ThreadLocal<EOUserAccount>();

	/**
	 * @return
	 */
	public static ApiSecurityContext getContext() {
		synchronized (ApiSecurityContext.class) {
			if(securityContext==null) {
				securityContext=new ApiSecurityContext();
			}
		}
		return securityContext;
	}

	/**
	 * @return
	 */
	public EOUserAccount getCurrentAccount() {
		return userAccountRequest.get();
	}

	/**
	 * @param eoUserAccount
	 */
	public void setCurrentAccount(EOUserAccount eoUserAccount) {
		this.userAccountRequest.set(eoUserAccount);
	}

}
