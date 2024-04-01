package com.brijframwork.authorization.service;

public interface MailService {
	
	public Boolean sendMail(String subject, String emailFrom,String emailTo,String body);

}
