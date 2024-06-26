package com.example.kimandpark_backend.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.kimandpark_backend.Repository.UserRepository;
import com.example.kimandpark_backend.domain.User;

import lombok.RequiredArgsConstructor;

/**
 * packageName :  com.example.kimandpark_backend.service
 * fileName : UserDetailService
 * author :  Park Byeong-mok
 * date : 2024-03-20(020) 
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2024-03-20(020)                Park Byeong-mok             최초 생성
 */

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
			Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())));

	}

} // end class
