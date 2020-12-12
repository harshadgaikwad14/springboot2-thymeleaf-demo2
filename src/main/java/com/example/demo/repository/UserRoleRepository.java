package com.example.demo.repository;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.UserRole;
import com.example.demo.rowmapper.UserRoleRowMapper;

@Repository
public class UserRoleRepository {

	Logger logger = LoggerFactory.getLogger(UserRoleRepository.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private UserRoleRowMapper userRoleRowMapper;

	public List<UserRole> findByUserId(long userId) {
		final String userQuery = "select user_id As userId,role_id As roleId from users_roles where user_id=:userId";

		try {
			final SqlParameterSource parameters = new MapSqlParameterSource("userId", userId);

			final List<UserRole> userRoles = namedParameterJdbcTemplate.query(userQuery, parameters, userRoleRowMapper);
			logger.info("userRoles : {} ", userRoles);

			return userRoles;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return Collections.emptyList();

	}

	public UserRole findByUserIdRoleId(long userId, long roleId) {

		logger.info("userId : {} - roleId : {}", userId, roleId);

		final String userQuery = "select user_id As userId,role_id As roleId from users_roles where user_id=:userId and role_id=:roleId";

		try {
			final SqlParameterSource parameters = new MapSqlParameterSource("userId", userId).addValue("roleId",
					roleId);

			final UserRole userRole = namedParameterJdbcTemplate.queryForObject(userQuery, parameters,
					userRoleRowMapper);
			logger.info("userRole : {} ", userRole);

			return userRole;

		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;

	}
	@Transactional
	public int save(long userId, long roleId) {

		final UserRole userRole = findByUserIdRoleId(userId, roleId);

		if (userRole != null) {
			return -1;
		}

		final String userQuery = "INSERT INTO users_roles (user_id, role_id) VALUES (:userId, :roleId)";

		final SqlParameterSource parameters = new MapSqlParameterSource("userId", userId).addValue("roleId", roleId);

		final int createStatus = namedParameterJdbcTemplate.update(userQuery, parameters);
		logger.info("createStatus : {} ", createStatus);
		return createStatus;
	}

	public int updateById(long userId, long oldRoleId, long newRoleId) {

		final UserRole userRole = findByUserIdRoleId(userId, oldRoleId);

		if (userRole != null) {
			return -1;
		}

		final String userQuery = "UPDATE users_roles SET role_id=:newRoleId WHERE  user_id=:userId AND role_id=:oldRoleId";

		final SqlParameterSource parameters = new MapSqlParameterSource("userId", userId)
				.addValue("oldRoleId", oldRoleId).addValue("newRoleId", newRoleId);

		final int updateStatus = namedParameterJdbcTemplate.update(userQuery, parameters);
		logger.info("updateStatus : {} ", updateStatus);
		return updateStatus;
	}

	public int deleteById(long userId, long roleId) {

		final UserRole userRole = findByUserIdRoleId(userId, roleId);

		if (userRole != null) {
			return -1;
		}

		final String userQuery = "DELETE FROM users_roles WHERE  user_id=:userId AND role_id=:roleId";

		final SqlParameterSource parameters = new MapSqlParameterSource("userId", userId).addValue("roleId", roleId);

		final int deleteStatus = namedParameterJdbcTemplate.update(userQuery, parameters);
		logger.info("deleteStatus : {} ", deleteStatus);
		return deleteStatus;
	}
}
