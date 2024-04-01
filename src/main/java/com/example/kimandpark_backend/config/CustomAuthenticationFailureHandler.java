package com.example.kimandpark_backend.config;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json; charset=utf-8");

		if (exception instanceof BadCredentialsException) {
			response.getWriter().print("{\"success\": false, \"message\": \"로그인 정보가 맞지 않거나, 회원정보가 없습니다.\"}");
		} else {
			response.getWriter().print("{\"success\": false, \"message\": \"인증에 실패하였습니다. 고객센터로 문의 바랍니다.\"}");
		}

		response.getWriter().flush();

	}

} // end class
