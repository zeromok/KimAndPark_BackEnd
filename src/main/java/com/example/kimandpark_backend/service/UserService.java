package com.example.kimandpark_backend.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.kimandpark_backend.Repository.UserRepository;
import com.example.kimandpark_backend.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public long save(User user) {
		return userRepository.save(User.builder()
			.email(user.getEmail())
			.password(bCryptPasswordEncoder.encode(user.getPassword()))
			.build()).getSeq();
	}


} // end class
