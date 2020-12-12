package com.example.demo.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.example.demo.model.UserRole;

@Component
public class UserRoleRowMapper implements RowMapper<UserRole> {

	public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
		final UserRole userRole = new UserRole();
		userRole.setUserId(rs.getLong("userId"));

		userRole.setRoleId(rs.getLong("roleId"));

		return userRole;
	}

}