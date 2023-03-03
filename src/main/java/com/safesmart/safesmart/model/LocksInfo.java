package com.safesmart.safesmart.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class LocksInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	

	private String lockType; 
	
	private LocalDateTime lockTime;
	
	private String lockStatus;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = UserInfo.class)
	@JoinColumn(name="userId")
	private UserInfo users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	

	public String getLockType() {
		return lockType;
	}

	public void setLockType(String lockType) {
		this.lockType = lockType;
	}

	public LocalDateTime getLockTime() {
		return lockTime;
	}

	public void setLockTime(LocalDateTime lockTime) {
		this.lockTime = lockTime;
	}

	public String getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}

	public UserInfo getUsers() {
		return users;
	}

	public void setUsers(UserInfo users) {
		this.users = users;
	}
	
	
}
