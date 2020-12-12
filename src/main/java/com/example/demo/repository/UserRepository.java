package com.example.demo.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.rowmapper.UserRowMapper;

@Repository
public class UserRepository {

	Logger logger = LoggerFactory.getLogger(UserRepository.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private UserRowMapper userRowMapper;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;
	
	
	

	public User findByUsername(String username) {
		final String userQuery = "select id,username,password from user where username = :username";

		try {
			
			final SqlParameterSource parameters = new MapSqlParameterSource("username", username);

			final User user = namedParameterJdbcTemplate.queryForObject(userQuery, parameters, userRowMapper);
			logger.info("user : {} ", user);
			final List<Role> roles = roleRepository.findByUserId(user.getId());
			logger.info("roles : {} ", roles);
			user.setRoles(roles.stream().collect(Collectors.toSet()));

			logger.info("user : {} ", user);
			return user;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public User findByUserId(long userId) {
		final String userQuery = "select id,username,password from user where id = :userId";

		try {
			final SqlParameterSource parameters = new MapSqlParameterSource("userId", userId);

			final User user = namedParameterJdbcTemplate.queryForObject(userQuery, parameters, userRowMapper);
			logger.info("user : {} ", user);
			final List<Role> roles = roleRepository.findByUserId(user.getId());
			logger.info("roles : {} ", roles);
			user.setRoles(roles.stream().collect(Collectors.toSet()));

			logger.info("user : {} ", user);
			return user;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
		
	}

	@Transactional
	public long save(User user) {
		logger.info("user : {} ",user);
		User exUser = findByUsername(user.getUsername());
		logger.info("exUser : {} ",exUser);
		if (exUser != null) {
			return exUser.getId();
		}

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		final String userQuery = "INSERT INTO user (password, username) VALUES (:password, :username)";

		final SqlParameterSource parameters = new MapSqlParameterSource("password", user.getPassword())
				.addValue("username", user.getUsername());

		final int createStatus = namedParameterJdbcTemplate.update(userQuery, parameters, keyHolder);
		logger.info("createStatus : {} ", createStatus);
		final long userId = keyHolder.getKey().longValue();
		for (Role role : user.getRoles()) {

			long roleId = roleRepository.save(role);
			logger.info("roleId : {} ", roleId);
			userRoleRepository.save(userId, roleId);
		}

		return userId;
	}

	public int updateById(long userId, User user) {
		final String userQuery = "UPDATE user SET username=:username WHERE  id=:userId";

		final SqlParameterSource parameters = new MapSqlParameterSource("userId", userId).addValue("username",
				user.getUsername());

		final int updateStatus = namedParameterJdbcTemplate.update(userQuery, parameters);
		logger.info("updateStatus : {} ", updateStatus);
		return updateStatus;
	}

	public int deleteById(long userId) {
		User exUser = findByUserId(userId);

		if (exUser != null) {
			return 0;
		}
		
		final String userQuery = "DELETE FROM user WHERE  id=:userId";

		final SqlParameterSource parameters = new MapSqlParameterSource("userId", userId);

		final int deleteStatus = namedParameterJdbcTemplate.update(userQuery, parameters);
		logger.info("deleteStatus : {} ", deleteStatus);
		return deleteStatus;
	}
}
