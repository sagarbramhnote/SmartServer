package com.safesmart.safesmart.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class BillValidator {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String billAcceptorNo;
	
	private String billAcceptorName;
	
	private String brandName;
	
	private String modelName;
	
	private String machineType;
	
	private String storageCapacity;
	
	private boolean active;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = StoreInfo.class)
	@JoinColumn(name="storeId")
	private StoreInfo storeinfob;
	
	

	public StoreInfo getStoreinfob() {
		return storeinfob;
	}

	public void setStoreinfob(StoreInfo storeinfob) {
		this.storeinfob = storeinfob;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBillAcceptorNo() {
		return billAcceptorNo;
	}

	public void setBillAcceptorNo(String billAcceptorNo) {
		this.billAcceptorNo = billAcceptorNo;
	}

	public String getBillAcceptorName() {
		return billAcceptorName;
	}

	public void setBillAcceptorName(String billAcceptorName) {
		this.billAcceptorName = billAcceptorName;
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

	public String getMachineType() {
		return machineType;
	}

	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}

	public String getStorageCapacity() {
		return storageCapacity;
	}

	public void setStorageCapacity(String storageCapacity) {
		this.storageCapacity = storageCapacity;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
