package com.example.demo.init;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;

@Service
public class DbInit implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(DbInit.class);
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private RoleRepository roleRepository;
	private UserRoleRepository userRoleRepository;

	public DbInit(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}

	@Override
	public void run(String... args) {

		logger.info("****** db init initiated ******");

		User user1 = new User();
		user1.setUsername("user");
		user1.setPassword(passwordEncoder.encode("user"));
		
		User user2 = new User();
		user2.setUsername("admin");
		user2.setPassword(passwordEncoder.encode("admin"));
		
		User user3 = new User();
		user3.setUsername("manager");
		user3.setPassword(passwordEncoder.encode("manager"));

		Role role1 = new Role();
		role1.setName("ROLE_USER");

		Role role2 = new Role();
		role2.setName("ROLE_ADMIN");

		Role role3 = new Role();
		role3.setName("ROLE_MANAGER");
		user1.getRoles().addAll(Arrays.asList(role1));
		user2.getRoles().addAll(Arrays.asList(role1, role2));
		user3.getRoles().addAll(Arrays.asList(role1, role2, role3));
		long userId = userRepository.save(user1);
		logger.info("userId : {}", userId);
		long userId2 = userRepository.save(user2);
		logger.info("userId2 : {}", userId2);
		long userId3 = userRepository.save(user3);
		logger.info("userId3 : {}", userId3);
		logger.info("****** db init ended ******");
	}
}
