package com.safesmart.safesmart.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Kiosk {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String kioskId;
	
	private String kioskName;
	
	private String brandName;
	
	private String modelName;
	
	private String cpu;
	
	private String hdd;
	
	private String ramMemory;
	
	private String screenSize;
	
	private String ipAddress;
	
	private String macAddress;
	
	private boolean active;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = StoreInfo.class)
	@JoinColumn(name="storeId")
	private StoreInfo storeinfok;
	
	


	public StoreInfo getStoreinfok() {
		return storeinfok;
	}



	public void setStoreinfok(StoreInfo storeinfok) {
		this.storeinfok = storeinfok;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getKioskId() {
		return kioskId;
	}



	public void setKioskId(String kioskId) {
		this.kioskId = kioskId;
	}



	public String getKioskName() {
		return kioskName;
	}



	public void setKioskName(String kioskName) {
		this.kioskName = kioskName;
	}



	public String getBrandName() {
		return brandName;
	}



	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}



	public String getModelName() {
		return modelName;
	}



	public void setModelName(String modelName) {
		this.modelName = modelName;
	}



	public String getCpu() {
		return cpu;
	}



	public void setCpu(String cpu) {
		this.cpu = cpu;
	}



	public String getHdd() {
		return hdd;
	}



	public void setHdd(String hdd) {
		this.hdd = hdd;
	}



	public String getRamMemory() {
		return ramMemory;
	}



	public void setRamMemory(String ramMemory) {
		this.ramMemory = ramMemory;
	}



	public String getScreenSize() {
		return screenSize;
	}


	public String getIpAddress() {
		return ipAddress;
	}



	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}



	public String getMacAddress() {
		return macAddress;
	}



	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}



	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}



	public boolean isActive() {
		return active;
	}



	public void setActive(boolean active) {
		this.active = active;
	}
	
	

}
