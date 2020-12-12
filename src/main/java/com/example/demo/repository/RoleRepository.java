package com.example.demo.repository;

import java.util.Collections;
import java.util.List;

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
import com.example.demo.rowmapper.RoleRowMapper;

@Repository
public class RoleRepository {

	Logger logger = LoggerFactory.getLogger(RoleRepository.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private RoleRowMapper roleRowMapper;

	public List<Role> findByUserId(long userId) {
		final String userQuery = "select r.id,r.name from role r LEFT OUTER JOIN users_roles ur on r.id=ur.role_id where ur.user_id=:userId";

		final SqlParameterSource parameters = new MapSqlParameterSource("userId", userId);
		try {
			final List<Role> roles = namedParameterJdbcTemplate.query(userQuery, parameters, roleRowMapper);
			logger.info("roles : {} ", roles);
			return roles;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Collections.emptyList();
	}

	public Role findById(long roleId) {
		final String userQuery = "select r.id,r.name from role r where r.id=:roleId";

		final SqlParameterSource parameters = new MapSqlParameterSource("roleId", roleId);
		try {
			final Role role = namedParameterJdbcTemplate.queryForObject(userQuery, parameters, roleRowMapper);
			logger.info("role : {} ", role);
			return role;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;

	}

	public Role findByName(String name) {
		final String userQuery = "select r.id,r.name from role r where r.name=:name";

		final SqlParameterSource parameters = new MapSqlParameterSource("name", name);

		try {

			final Role role = namedParameterJdbcTemplate.queryForObject(userQuery, parameters, roleRowMapper);
			logger.info("role : {} ", role);
			return role;
		} catch (Exception e) {

		}

		return null;
	}
	@Transactional
	public long save(Role role) {
		logger.info("role : {} ",role);
		Role exRole = findByName(role.getName());
		logger.info("exRole : {} ",exRole);
		if (exRole != null) {
			return exRole.getId();
		}

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		final String userQuery = "INSERT INTO role (name) VALUES (:roleName)";

		final SqlParameterSource parameters = new MapSqlParameterSource("roleName", role.getName());

		final int createStatus = namedParameterJdbcTemplate.update(userQuery, parameters, keyHolder);
		logger.info("createStatus : {} ", createStatus);
		return keyHolder.getKey().longValue();
	}

	public int updateById(long roleId, Role role) {
		final String userQuery = "UPDATE role SET name=:roleName WHERE  id=:roleId";

		final SqlParameterSource parameters = new MapSqlParameterSource("roleId", roleId).addValue("roleName",
				role.getName());

		final int updateStatus = namedParameterJdbcTemplate.update(userQuery, parameters);
		logger.info("updateStatus : {} ", updateStatus);
		return updateStatus;
	}

	public int deleteById(long roleId) {

		Role exRole = findById(roleId);

		if (exRole != null) {
			return -1;
		}

		final String userQuery = "DELETE FROM role WHERE id=:roleId";

		final SqlParameterSource parameters = new MapSqlParameterSource("roleId", roleId);

		final int deleteStatus = namedParameterJdbcTemplate.update(userQuery, parameters);
		logger.info("deleteStatus : {} ", deleteStatus);
		return deleteStatus;
	}
}
