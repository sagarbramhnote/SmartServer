package com.safesmart.safesmart.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	
	private String memory;
	
	private String screenSize;
	
	private boolean active;
	
	


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
