package com.example.demo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;

	private Long roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "UserRole [userId=" + userId + ", roleId=" + roleId + "]";
	}

}
