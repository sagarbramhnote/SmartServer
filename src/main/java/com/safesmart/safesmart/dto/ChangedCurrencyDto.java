package com.safesmart.safesmart.dto;

public class ChangedCurrencyDto {
	
	private String currency;
	private String changeNeeded;
	private String depositedValue;
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getChangeNeeded() {
		return changeNeeded;
	}
	public void setChangeNeeded(String changeNeeded) {
		this.changeNeeded = changeNeeded;
	}
	public String getDepositedValue() {
		return depositedValue;
	}
	public void setDepositedValue(String depositedValue) {
		this.depositedValue = depositedValue;
	}
	public ChangedCurrencyDto(String currency, String changeNeeded, String depositedValue) {
		this.currency = currency;
		this.changeNeeded = changeNeeded;
		this.depositedValue = depositedValue;
	}
	@Override
	public String toString() {
		return "ChangedCurrencyDto [currency=" + currency + ", changeNeeded=" + changeNeeded + ", depositedValue="
				+ depositedValue + "]";
	}

}
