package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
	void save(User user);

	User findByUserName(String userName);

	User findByUserId(long userId);

	void deleteById(long id);
}
