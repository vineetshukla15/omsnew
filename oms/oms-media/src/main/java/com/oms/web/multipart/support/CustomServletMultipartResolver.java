package com.oms.web.multipart.support;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@Component
public class CustomServletMultipartResolver extends StandardServletMultipartResolver {

	@Override
	public boolean isMultipart(HttpServletRequest request) {
		if (!"post".equals(request.getMethod().toLowerCase()) && !"put".equals(request.getMethod().toLowerCase())) {
			return false;
		}
		String contentType = request.getContentType();
		return (contentType != null && contentType.toLowerCase().startsWith("multipart/"));
	}

}