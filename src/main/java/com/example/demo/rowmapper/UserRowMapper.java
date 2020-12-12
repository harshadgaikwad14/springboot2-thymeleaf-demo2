package com.example.demo.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;

@Component
public class UserRowMapper implements RowMapper<User> {

	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		final User user = new User();
		user.setId(rs.getLong("id"));
		user.setPassword(rs.getString("password"));
		user.setUsername(rs.getString("username"));

		return user;
	}

}