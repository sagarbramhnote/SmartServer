package com.safesmart.safesmart.mangerreport;

public class denominationDto {

	private String denominations;
	private String in;
	private String out;
	public String getDenominations() {
		return denominations;
	}
	public void setDenominations(String denominations) {
		this.denominations = denominations;
	}
	public String getIn() {
		return in;
	}
	public void setIn(String in) {
		this.in = in;
	}
	public String getOut() {
		return out;
	}
	public void setOut(String out) {
		this.out = out;
	}
	public denominationDto(String denominations, String in, String out) {
		super();
		this.denominations = denominations;
		this.in = in;
		this.out = out;
	}
	@Override
	public String toString() {
		return "denominationDto [denominations=" + denominations + ", in=" + in + ", out=" + out + "]";
	}
	

	
}
