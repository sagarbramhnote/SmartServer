package com.safesmart.safesmart.dto;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.safesmart.safesmart.common.CommonException;
import com.safesmart.safesmart.common.CommonExceptionMessage;

public class UserInfoRequest {

	private Long id;

	private String username;

	private String password;

	private String role;

	private boolean active;

	private String feature;
	
	// admin or manager can create users
	private Long loggedUserId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	
	public Long getLoggedUserId() {
		return loggedUserId;
	}

	public void setLoggedUserId(Long loggedUserId) {
		this.loggedUserId = loggedUserId;
	}

	public void validateRequiredAttributes() {

		if (StringUtils.isEmpty(username)) {
			throw CommonException.CreateException(CommonExceptionMessage.REQUIRED_ATTRIBUTE, "Username");
		}
		if (StringUtils.isEmpty(password)) {
			throw CommonException.CreateException(CommonExceptionMessage.REQUIRED_ATTRIBUTE, "Password");
		}
		if (StringUtils.isEmpty(role)) {
			throw CommonException.CreateException(CommonExceptionMessage.REQUIRED_ATTRIBUTE, "Role");
		}

		String regex = "[0-9]+";
		// Compile the ReGex
		Pattern p = Pattern.compile(regex);
		if (password.length() != 4 || !p.matcher(password).matches()) {
			throw CommonException.CreateException(CommonExceptionMessage.VALIDATE_PIN);
		}

	}

	public void validateLoginRequired() {
		if (StringUtils.isEmpty(password)) {
			throw CommonException.CreateException(CommonExceptionMessage.REQUIRED_ATTRIBUTE, "Password");
		}
		String regex = "[0-9]+";
		// Compile the ReGex
		Pattern p = Pattern.compile(regex);
		if (password.length() != 4 || !p.matcher(password).matches()) {
			throw CommonException.CreateException(CommonExceptionMessage.VALIDATE_PIN);
		}

		if (StringUtils.isEmpty(feature)) {
			throw CommonException.CreateException(CommonExceptionMessage.REQUIRED_ATTRIBUTE, "Feautre");
		}
	}
}
