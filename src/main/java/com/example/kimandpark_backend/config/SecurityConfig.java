package com.example.kimandpark_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
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
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.headers(header -> header.frameOptions(
				HeadersConfigurer.FrameOptionsConfig::disable)) // 프레임 옵션 비활성화 For H2 console
			.csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화 For H2 console
			.authorizeHttpRequests(request -> {
				request
					.requestMatchers("/login", "/", "/static/**", "/h2-console/**").permitAll()
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.requestMatchers("/user/**").hasRole("USER")
					.anyRequest().authenticated();
			})
			.formLogin(login ->
				login
					.loginPage("/login")
					.successHandler(new CustomAuthenticationSuccessHandler())
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

	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

} // end class
