package com.brijframework.authorization.service;

import java.util.Map;

public interface TemplateService {

	String buildHtml(String template, Map<String, Object> messageMap);

}
