package com.safesmart.safesmart.dto;

import java.util.List;

public class EodResponse {
	private int totalEodBillsAmount;
	
	private List<EODReport> eodReport;
	
	private StoreInfoResponse storeInfoResponse;
	
	private int stateBankBalance;
	
	private int changeRequestBalance;
	
	

	public int getTotalEodBillsAmount() {
		return totalEodBillsAmount;
	}

	public void setTotalEodBillsAmount(int totalEodBillsAmount) {
		this.totalEodBillsAmount = totalEodBillsAmount;
	}

	public List<EODReport> getEodReport() {
		return eodReport;
	}

	public void setEodReport(List<EODReport> eodReport) {
		this.eodReport = eodReport;
	}

	public StoreInfoResponse getStoreInfoResponse() {
		return storeInfoResponse;
	}

	public void setStoreInfoResponse(StoreInfoResponse storeInfoResponse) {
		this.storeInfoResponse = storeInfoResponse;
	}

	public int getStateBankBalance() {
		return stateBankBalance;
	}

	public void setStateBankBalance(int stateBankBalance) {
		this.stateBankBalance = stateBankBalance;
	}

	public int getChangeRequestBalance() {
		return changeRequestBalance;
	}

	public void setChangeRequestBalance(int changeRequestBalance) {
		this.changeRequestBalance = changeRequestBalance;
	}

	

}
