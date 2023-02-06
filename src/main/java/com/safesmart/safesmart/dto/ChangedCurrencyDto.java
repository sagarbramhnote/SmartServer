package com.safesmart.safesmart.dto;

public class ChangedCurrencyDto {
	
	private String currency;
	private String currencytotal;
	private String changeNeeded;
	private String depositedValue;
	private String newcurrencytotal;
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
	
	
	public String getCurrencytotal() {
		return currencytotal;
	}
	public void setCurrencytotal(String currencytotal) {
		this.currencytotal = currencytotal;
	}
	
	
	
	public String getNewcurrencytotal() {
		return newcurrencytotal;
	}
	public void setNewcurrencytotal(String newcurrencytotal) {
		this.newcurrencytotal = newcurrencytotal;
	}
	public ChangedCurrencyDto(String currency, String currencytotal, String changeNeeded, String depositedValue,
			String newcurrencytotal) {
		super();
		this.currency = currency;
		this.currencytotal = currencytotal;
		this.changeNeeded = changeNeeded;
		this.depositedValue = depositedValue;
		this.newcurrencytotal = newcurrencytotal;
	}
	@Override
	public String toString() {
		return "ChangedCurrencyDto [currency=" + currency + ", currencytotal=" + currencytotal + ", changeNeeded="
				+ changeNeeded + ", depositedValue=" + depositedValue + ", newcurrencytotal=" + newcurrencytotal + "]";
	}



}
