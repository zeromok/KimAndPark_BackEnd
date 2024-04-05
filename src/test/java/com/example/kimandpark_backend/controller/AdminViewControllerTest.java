package com.example.kimandpark_backend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName :  com.example.kimandpark_backend.controller
 * fileName : AdminViewControllerTest
 * author :  Park Byeong-mok
 * date : 2024-04-05(005) 
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2024-04-05(005)                Park Byeong-mok             최초 생성
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class AdminViewControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		;
		;
	}

	@Test
	@Order(1)
	@DisplayName("관리자 계정 접속에 성공 후 관리자 페이지로 이동한다.")
	void admin() throws Exception {
		mockMvc.perform(
				post("/login")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.content("username=admin@gmail.com&password=1234")
			)
			.andExpect(
				status()
					.is3xxRedirection()
			)
			.andExpect(
				redirectedUrl("/admin")
			);
	}

} // end class