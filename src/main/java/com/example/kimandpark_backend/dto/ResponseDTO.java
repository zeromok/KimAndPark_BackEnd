package com.example.kimandpark_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * packageName :  com.example.kimandpark_backend.dto
 * fileName : ResponseDTO
 * author :  Park Byeong-mok
 * date : 2024-03-21(021) 
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2024-03-21(021)                Park Byeong-mok             최초 생성
 */
@Data
@AllArgsConstructor
public class ResponseDTO {

	private boolean success;
	private String message;
	private Object data;

	public static ResponseDTO body(boolean success, String message, Object data) {
		return new ResponseDTO(success, message, data);
	}

} // end class
