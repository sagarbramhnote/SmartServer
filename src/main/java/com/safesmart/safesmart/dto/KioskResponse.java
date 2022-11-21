package com.safesmart.safesmart.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class KioskResponse {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String kioskId;
	
	private String kioskName;
	
	private String brandName;
	
	private String modelName;
	
	private String cpu;
	
	private String hdd;
	
	private String memory;
	
	private String screenSize;
	
	private boolean active;
	
	

	public KioskResponse() {
		super();
		// TODO Auto-generated constructor stub
	}



	public KioskResponse(Long id, String kioskId, String kioskName, String brandName, String modelName, String cpu,
			String hdd, String memory, String screenSize, boolean active) {
		super();
		this.id = id;
		this.kioskId = kioskId;
		this.kioskName = kioskName;
		this.brandName = brandName;
		this.modelName = modelName;
		this.cpu = cpu;
		this.hdd = hdd;
		this.memory = memory;
		this.screenSize = screenSize;
		this.active = active;
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



	public String getMemory() {
		return memory;
	}



	public void setMemory(String memory) {
		this.memory = memory;
	}



	public String getScreenSize() {
		return screenSize;
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
