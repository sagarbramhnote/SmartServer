package com.safesmart.safesmart.dto;

import java.util.List;

public class StoreInfoResponse {
	
	private Long id;

	private String storeName;

	private String corpStoreNo;

	private String serialNumber;

	private String address;

	private String bankName;

	private String accountNumber;

	private Double minimumBalance;

	private boolean configured;

	private String startTime;

	private String endTime;

	private List<Long> userIds;
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

	public boolean isConfigured() {
		return configured;
	}

	public void setConfigured(boolean configured) {
		this.configured = configured;
	}

	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public StoreInfoResponse(Long id, String storeName, String corpStoreNo, String serialNumber) {
		super();
		this.id = id;
		this.storeName = storeName;
		this.corpStoreNo = corpStoreNo;
		this.serialNumber = serialNumber;
	}

	public StoreInfoResponse() {
		// TODO Auto-generated constructor stub
	}

}
