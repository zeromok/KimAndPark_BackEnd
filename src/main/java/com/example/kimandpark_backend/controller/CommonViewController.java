package com.example.kimandpark_backend.controller;

import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Object> findPw(@RequestBody User email) {

		String pw = userService.findPw(email.getEmail());
		if (pw.equals("false")) {
			ResponseEntity.ok(ResponseDTO.body(false, "비밀번호를 찾을 수 없습니다.", ""));
		}
		return ResponseEntity.ok(ResponseDTO.body(true, "password", pw));
	}

	private boolean isValidEmail(String email) {
		return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
	}

} // end class
