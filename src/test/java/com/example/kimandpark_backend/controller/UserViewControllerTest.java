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
 * fileName : UserViewControllerTest
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
class UserViewControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		;
		;
	}

	@Test
	@Order(1)
	@DisplayName("유저 접속 성공 후 유저페이지로 이동한다.")
	void user() throws Exception {
		mockMvc
			.perform(
				post("/login")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.content("username=user01@gmail.com&password=1234")
			)
			.andExpect(
				status()
					.is3xxRedirection()
			)
			.andExpect(
				redirectedUrl("/users")
			);
	}

} // end class