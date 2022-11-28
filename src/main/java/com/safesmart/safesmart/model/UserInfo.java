package com.safesmart.safesmart.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserInfo {


	private Long id;

	private String username;

	private String password;

	private Role role;

	private LocalDate create_time;

	private boolean active;
	private String firstName;
	private String lastName;
	private String passLength;
	

	public String getPassLength() {
		return passLength;
	}

	public void setPassLength(String passLength) {
		this.passLength = passLength;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role
				+ ", create_time=" + create_time + ", active=" + active + ", firstName=" + firstName + ", lastName="
				+ lastName + ", passLength=" + passLength + ", mobile=" + mobile + ", email=" + email + ", storeInfo="
				+ storeInfo + ", insertBills=" + insertBills + ", lastLoginTime=" + lastLoginTime + "]";
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * only for truck guy
	 */
	private String mobile;

	private String email;

	private StoreInfo storeInfo;

	private List<InsertBill> insertBills = new ArrayList<InsertBill>();

	private LocalDateTime lastLoginTime;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	@ManyToOne(cascade = CascadeType.MERGE)
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public LocalDate getCreate_time() {
		return create_time;
	}

	public void setCreate_time(LocalDate create_time) {
		this.create_time = create_time;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	public List<InsertBill> getInsertBills() {
		return insertBills;
	}

	public void setInsertBills(List<InsertBill> insertBills) {
		this.insertBills = insertBills;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInfo other = (UserInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public boolean checkfeature(String feature) {
		if (this.role.getFeatures().contains("All")) {
			return true;
		}

		return this.role.getFeatures().contains(feature);
	}

	@ManyToOne
	public StoreInfo getStoreInfo() {
		return storeInfo;
	}

	public void setStoreInfo(StoreInfo storeInfo) {
		this.storeInfo = storeInfo;
	}

	public LocalDateTime getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(LocalDateTime lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

}
