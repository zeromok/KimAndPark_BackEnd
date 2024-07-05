package com.example.kimandpark_backend.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kimandpark_backend.domain.User;
import com.example.kimandpark_backend.dto.ResponseDTO;
import com.example.kimandpark_backend.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommonViewController {

	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping(value = "/signup")
	public ResponseEntity<Object> signUp(User formData) {
		if (formData.getEmail().isEmpty() || formData.getPassword().isEmpty() || !isValidEmail(formData.getEmail())) {
			return ResponseEntity.badRequest().body(ResponseDTO.body(false, "데이터가 올바르지 않습니다.", ""));
		}

		User userData = new User(formData.getEmail(), formData.getPassword());

		try {
			long seq = userService.save(userData);

			return seq == 0 ?
				ResponseEntity.internalServerError().body(ResponseDTO.body(false, "회원가입에 실패했습니다. 다시 시도해주세요.", "")) :
				ResponseEntity.ok().body(ResponseDTO.body(true, "회원가입에 성공했습니다.", seq));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.internalServerError()
				.body(ResponseDTO.body(false, "회원가입에 실패했습니다. 다시 시도해주세요.", e.getMessage()));
		}

	}

	@PostMapping("/find-pw")
	public ResponseEntity<Object> findPw(@RequestBody User loginInfo) {

		Optional<User> user = userService.findByEmail(loginInfo.getEmail());

		if (user.isEmpty()) {
			return ResponseEntity.ok(ResponseDTO.body(false, "비밀번호를 찾을 수 없습니다.", ""));
		}

		// 임시 비밀번호 생성
		String tempPassword = UUID.randomUUID().toString().substring(0, 8);

		User newUser = new User(loginInfo.getEmail(), bCryptPasswordEncoder.encode(tempPassword));

		try {
			if (userService.deleteByEmail(loginInfo.getEmail())) {
				long seq = userService.save(newUser);

				return seq == 0 ?
					ResponseEntity.internalServerError()
						.body(ResponseDTO.body(false, "비밀번호 재설정 중 오류가 발생했습니다. 다시 시도해 주세요.", "")) :
					ResponseEntity.ok()
						.body(ResponseDTO.body(true, "비밀번호 변경에 성공했습니다. 새로운 정보로 로그인 해주세요.", newUser.getPassword()));
			} else {
				return ResponseEntity.internalServerError()
					.body(ResponseDTO.body(false, "이메일 주소를 가진 사용자를 삭제하는 중 오류가 발생했습니다. 다시 시도해 주세요.", ""));
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.internalServerError()
				.body(ResponseDTO.body(false, "비밀번호 재설정 과정에서 예기치 않은 오류가 발생했습니다. 다시 시도해 주세요.", e.getMessage()));
		}
	}

	private boolean isValidEmail(String email) {
		return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
	}

} // end class
