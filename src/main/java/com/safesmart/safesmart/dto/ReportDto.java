package com.safesmart.safesmart.dto;

public class ReportDto {

	private String userName;

	private String reportName;

	private StoreInfoResponse storeInfoResponse;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public StoreInfoResponse getStoreInfoResponse() {
		return storeInfoResponse;
	}

	public void setStoreInfoResponse(StoreInfoResponse storeInfoResponse) {
		this.storeInfoResponse = storeInfoResponse;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

}
