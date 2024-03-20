package com.example.kimandpark_backend.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.kimandpark_backend.service.UserDetailService;

import lombok.RequiredArgsConstructor;

/**
 * packageName :  com.example.kimandpark_backend.config
 * fileName : SecurityConfig
 * author :  Park Byeong-mok
 * date : 2024-03-20(020) 
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2024-03-20(020)                Park Byeong-mok             최초 생성
 */

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final UserDetailService userService;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer(){
		return (web) -> web.ignoring() // 시큐리티의 인증, 인가 모든 곳에 적용하지 않음
			.requestMatchers(toH2Console()) // H2 DB 관련
			.requestMatchers("/static/**"); // 정적메서드
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(request -> {
				request
					.requestMatchers("/login").permitAll()
					.anyRequest().authenticated();
			})
			.formLogin(login ->
				login
					.loginPage("/login")
					.defaultSuccessUrl("/")
			)
			.logout(logout ->
				logout
					.logoutUrl("/logout")
					.logoutSuccessUrl("/login")
					.invalidateHttpSession(true)
			)
			.authenticationProvider(daoAuthenticationProvider());

		return httpSecurity.build();
	}

	private AuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

		daoAuthenticationProvider.setUserDetailsService(userService);
		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

		return daoAuthenticationProvider;
	}

	private PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

} // end class
