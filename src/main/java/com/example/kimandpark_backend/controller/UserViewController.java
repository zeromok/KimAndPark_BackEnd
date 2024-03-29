package com.example.kimandpark_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kimandpark_backend.dto.ResponseDTO;

/**
 * packageName :  com.example.kimandpark_backend.controller
 * fileName : UserViewController
 * author :  Park Byeong-mok
 * date : 2024-03-21(021) 
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2024-03-21(021)                Park Byeong-mok             최초 생성
 */
@RestController
@RequestMapping("/users")
public class UserViewController {

	@GetMapping()
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Object> user() {
		return ResponseEntity.ok(ResponseDTO.body(true, "안농하세오?유저에오!", ""));
	}

} // end class
