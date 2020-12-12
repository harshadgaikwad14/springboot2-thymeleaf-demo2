package com.example.demo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void save(User user) {
		logger.info("save admin panel user");
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		final Role role = roleRepository.findByName("ROLE_USER");
		if (role != null) {

			user.getRoles().add(role);
			userRepository.save(user);

		}
	}

	@Override
	public User findByUserName(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public void deleteById(long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findByUserId(long userId) {
		return userRepository.findByUserId(userId);
	}
}
