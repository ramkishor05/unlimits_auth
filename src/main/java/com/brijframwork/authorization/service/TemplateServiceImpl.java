package com.brijframwork.authorization.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class TemplateServiceImpl implements TemplateService {

	private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);
	
	@Autowired
	private TemplateEngine templateEngine;

	@Override
	public String buildHtml(String template, Map<String, Object> messageMap) {
		log.debug("TemplateService::buildHtml() start.");
		final Context context = new Context();
		context.setVariables(messageMap);
		try {
			return templateEngine.process(template, context);
		}finally {
			log.debug("TemplateService::buildHtml() end.");
		}
	}
	
}
