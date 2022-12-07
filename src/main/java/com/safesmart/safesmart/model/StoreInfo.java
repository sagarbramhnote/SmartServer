package com.safesmart.safesmart.model;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class StoreInfo {

	private Long id;

	private String storeName;

	private String corpStoreNo;

	private String serialNumber;

	private String address;

	private String bankName;

	private String accountNumber;

	private Double minimumBalance;
	
	private boolean configured;
	
	private LocalTime startTime;
	
	private LocalTime endTime;

	private List<UserInfo> users;
	 
	private List<Locks> locks;
	
	private List<Printer> printer;


	@OneToMany(targetEntity=Locks.class, mappedBy="storeInfo", fetch=FetchType.EAGER)
	public List<Locks> getLocks() {
		return locks;
	}

	public void setLocks(List<Locks> locks) {
		this.locks = locks;
	}


	
	@OneToMany(targetEntity=Printer.class, mappedBy="storeinfop")
	public List<Printer> getPrinter() {
		return printer;
	}

	public void setPrinter(List<Printer> printer) {
		this.printer = printer;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getCorpStoreNo() {
		return corpStoreNo;
	}

	public void setCorpStoreNo(String corpStoreNo) {
		this.corpStoreNo = corpStoreNo;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Double getMinimumBalance() {
		return minimumBalance;
	}

	public void setMinimumBalance(Double minimumBalance) {
		this.minimumBalance = minimumBalance;
	}

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,mappedBy ="storeInfo" )
	public List<UserInfo> getUsers() {
		return users;
	}

	public void setUsers(List<UserInfo> users) {
		this.users = users;
	}

	public boolean isConfigured() {
		return configured;
	}

	public void setConfigured(boolean configured) {
		this.configured = configured;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	
	@Override
	public String toString() {
		return "StoreInfo [id=" + id + ", storeName=" + storeName + ", corpStoreNo=" + corpStoreNo + ", serialNumber="
				+ serialNumber + ", address=" + address + ", bankName=" + bankName + ", accountNumber=" + accountNumber
				+ ", minimumBalance=" + minimumBalance + ", configured=" + configured + ", users=" + users + ", locks="
				+ locks + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
