package com.example.demo.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.example.demo.model.Role;


@Component
public class RoleRowMapper implements RowMapper<Role> {

	public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
		final Role role = new Role();
		role.setId(rs.getLong("id"));

		role.setName(rs.getString("name"));

		return role;
	}

}