package com.blog.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import com.blog.repositories.UserRepository;
import com.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User saveUser = this.userRepository.save(user);
		return this.userToDto(saveUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer id) {
		User user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		return this.userToDto(this.userRepository.save(user));
		
	}

	@Override
	public UserDto getUserById(Integer userId) {
	    Optional<User> userOptional = userRepository.findById(userId);
	    User user = userOptional.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
	    return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users = this.userRepository.findAll();
		 List<UserDto> userDtos= users.stream().map(user-> this.userToDto(user)).collect(Collectors.toList());
		 return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user =  this.userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		this.userRepository.delete(user);
	}
	
	private User dtoToUser(UserDto userDto)
	{
		User user = new User();
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		System.out.println("UserDto id value: " + userDto.getId());
		if (userDto.getId() != null) {
	        user.setId(userDto.getId().intValue());
	    }
		
		return user;
	}
	
	public UserDto userToDto(User user) {
		UserDto userDto = new UserDto();
		
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setAbout(user.getAbout());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		
		return userDto;
		
	}

}
