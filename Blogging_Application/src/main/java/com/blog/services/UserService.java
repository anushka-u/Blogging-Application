package com.blog.services;

import com.blog.payloads.UserDto;

import java.util.*;

import org.springframework.stereotype.Service;


public interface UserService {
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer id);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUser();
	void deleteUser(Integer userId);
	

}
