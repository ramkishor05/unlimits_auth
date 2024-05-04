package com.brijframework.authorization.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {
	
	private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);
	
	@Autowired
	private JavaMailSender emailSender;
	
	@Override
	public Boolean sendMail(String subject, String emailFrom, String emailTo, String body) {
		log.debug("MailService::sendMail start.");
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper msg = new MimeMessageHelper(message);
			msg.setTo(emailTo);
			msg.setFrom(emailFrom);
			msg.setText(body, true);
			msg.setSubject(subject);
			emailSender.send(message);
			log.debug("MailService sent to email.");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		log.debug("MailService::sendMail end.");
		return true;
	}

}
