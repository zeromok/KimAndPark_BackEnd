package com.example.kimandpark_backend.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email).isPresent() ?
			userRepository.findByEmail(email) : Optional.empty();
	}

	@Transactional
	public boolean deleteByEmail(String email) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		if (userOptional.isPresent()) {
			userRepository.deleteByEmail(email);
			return true;
		} else {
			return false;
		}
	}

} // end class
