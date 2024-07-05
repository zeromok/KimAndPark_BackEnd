package com.example.kimandpark_backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kimandpark_backend.domain.User;

/**
 * packageName :  com.example.kimandpark_backend.Repository
 * fileName : UserRepository
 * author :  Daniel
 * date : 2024-03-20(020) 
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2024-03-20(020)                Daniel             최초 생성
 */
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	void deleteByEmail(String email);

} // end class
