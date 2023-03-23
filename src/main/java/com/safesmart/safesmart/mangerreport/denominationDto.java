package com.safesmart.safesmart.mangerreport;

public class denominationDto {

	private String Denominations;
	private String Deposited_Value;
	private String Change_Needed;
	@Override
	public String toString() {
		return "denominationDto [Denominations=" + Denominations + ", Deposited_Value=" + Deposited_Value
				+ ", Change_Needed=" + Change_Needed + "]";
	}
	public String getDenominations() {
		return Denominations;
	}
	public void setDenominations(String denominations) {
		Denominations = denominations;
	}
	public String getDeposited_Value() {
		return Deposited_Value;
	}
	public void setDeposited_Value(String deposited_Value) {
		Deposited_Value = deposited_Value;
	}
	public String getChange_Needed() {
		return Change_Needed;
	}
	public void setChange_Needed(String change_Needed) {
		Change_Needed = change_Needed;
	}
	public denominationDto(String denominations, String deposited_Value, String change_Needed) {
		super();
		Denominations = denominations;
		Deposited_Value = deposited_Value;
		Change_Needed = change_Needed;
	}

	
	

	
	
}
