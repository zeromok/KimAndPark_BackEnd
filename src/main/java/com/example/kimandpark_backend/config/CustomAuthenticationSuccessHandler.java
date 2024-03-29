package com.example.kimandpark_backend.config;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * packageName :  com.example.kimandpark_backend.config
 * fileName : CustomAuthenticationSuccessHandler
 * author :  Park Byeong-mok
 * date : 2024-03-28(028) 
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2024-03-28(028)                Park Byeong-mok             최초 생성
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

		System.out.println(roles.toString());

		if (roles.contains("ROLE_ADMIN")) {
			response.sendRedirect("/admin");
		} else if (roles.contains("ROLE_USER")) {
			response.sendRedirect("/users");
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().print("{\"success\": false, \"message\": \"회원가입 먼저 진행해주세요.\", \"data\": \"\"}");
			response.getWriter().flush();
		}
	}
}
