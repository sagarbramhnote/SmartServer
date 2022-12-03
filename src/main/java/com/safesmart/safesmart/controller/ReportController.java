package com.safesmart.safesmart.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.safesmart.safesmart.dto.DateRangedto;
import com.safesmart.safesmart.dto.EODReport;
import com.safesmart.safesmart.dto.EmployeeReportDto;
import com.safesmart.safesmart.dto.InsertBillsReportDto;
import com.safesmart.safesmart.dto.ManagerReportDto;
import com.safesmart.safesmart.dto.ReportDto;
import com.safesmart.safesmart.dto.ReprintReportDto;
import com.safesmart.safesmart.service.ReportService;

@RestController
@RequestMapping(value = "/reports")
@CrossOrigin
public class ReportController {

	@Autowired
	private ReportService reportService;

	@RequestMapping(value = "/endofdayReport/{userId}", method = RequestMethod.GET)
	public EODReport endOfDayReport(@PathVariable(value="userId") Long userId) {
		return reportService.endOfDayReport(userId);
	}

	@RequestMapping(value = "/testPrintReport",method = RequestMethod.GET)
	public ReportDto testPrintReport() {
		return reportService.testPrintReport();
	}

	@RequestMapping(value = "/rePrintReceipt", method = RequestMethod.GET)
	public ReprintReportDto rePrintReport() {
		return reportService.rePrintReport();
	}

	@RequestMapping(value = "/insertBillsReport/{transactionNumber}", method = RequestMethod.GET)
	public InsertBillsReportDto insertBillsReport(@PathVariable("transactionNumber") String transactionNumber) {
		
		return reportService.insertBillsReport(transactionNumber);
	}

	@RequestMapping(value = "/managerReport", method = RequestMethod.POST)
	public ManagerReportDto managerBillReport(@RequestBody DateRangedto dateRangedto) {
		return reportService.managerBillReport(dateRangedto);
	}
	
	@RequestMapping(value = "/employeeReportExport/{userId}/{sDate}/{endDate}",method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> employeeReportDataExport(@PathVariable("userId")Long userId, @PathVariable("sDate") String  sDate,@PathVariable("endDate")String endDate) throws IOException {
		DateRangedto dateRangedto = new DateRangedto() ;
		dateRangedto.setStartDate(sDate);
		dateRangedto.setEndDate(endDate);
		dateRangedto.validateRequest();
		ByteArrayInputStream in = reportService.reportToExcel(userId,dateRangedto);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename-report.xlsx");
		 return ResponseEntity
		         .ok()
		         .headers(headers)
		         .body(new InputStreamResource(in));

//		return reportService.reportToExcel(userId,dateRangedto);
	}
	@RequestMapping(value = "/employeeReport/{userId}",method = RequestMethod.POST)
	public EmployeeReportDto employeeReportData(@PathVariable("userId")Long userId, @RequestBody DateRangedto dateRangedto) {
		dateRangedto.validateRequest();
		return reportService.employeeReportData(userId,dateRangedto);
	}
	
	@RequestMapping(value = "/managerReport/{userId}",method = RequestMethod.POST)
	public EmployeeReportDto managerReportData(@PathVariable("userId")Long userId, @RequestBody DateRangedto dateRangedto) {
		dateRangedto.validateRequest1();
		return reportService.managerReportData(userId,dateRangedto);
	}
	
}
