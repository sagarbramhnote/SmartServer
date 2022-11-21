package com.safesmart.safesmart.dto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeReportResponse {

	/**
	 * we can use this name as name of employee or date.
	 */
	private String name;

	private Collection<BillResponse> data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<BillResponse> getData() {
		return data;
	}

	public void setData(Collection<BillResponse> data) {
		this.data = data;
	}

}
