package com.example.kimandpark_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kimandpark_backend.dto.ResponseDTO;

@RestController
@RequestMapping("/admin")
public class AdminViewController {

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> admin() {
		return ResponseEntity.ok(ResponseDTO.body(true, "내가관리자다!", ""));
	}

} // end class
