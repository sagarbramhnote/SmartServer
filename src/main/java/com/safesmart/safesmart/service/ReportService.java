package com.safesmart.safesmart.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safesmart.safesmart.dto.BillResponse;
import com.safesmart.safesmart.dto.DateRangedto;
import com.safesmart.safesmart.dto.EODReport;
import com.safesmart.safesmart.dto.EmployeeReportDto;
import com.safesmart.safesmart.dto.EmployeeReportResponse;
import com.safesmart.safesmart.dto.InsertBillResponse;
import com.safesmart.safesmart.dto.InsertBillsReportDto;
import com.safesmart.safesmart.dto.ManagerReportDto;
import com.safesmart.safesmart.dto.ReportDto;
import com.safesmart.safesmart.dto.ReprintReportDto;
import com.safesmart.safesmart.dto.StoreInfoResponse;
import com.safesmart.safesmart.model.Dollar;
import com.safesmart.safesmart.model.InsertBill;
import com.safesmart.safesmart.model.SequenceInfo;
import com.safesmart.safesmart.model.StoreInfo;
import com.safesmart.safesmart.model.UserInfo;
import com.safesmart.safesmart.repository.InsertBillRepository;
import com.safesmart.safesmart.repository.SequenceInfoRepository;
import com.safesmart.safesmart.repository.UserInfoRepository;
import com.safesmart.safesmart.util.DateUtil;
import com.safesmart.safesmart.util.EmailTemplate;

@Service
public class ReportService {

	@Autowired
	private StoreInfoService storeInfoService;

	@Autowired
	private SequenceInfoRepository sequenceInfoRepository;

	@Autowired
	private InsertBillRepository insertBillRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private EmailTemplate emailTemplate;

	public ReprintReportDto rePrintReport() {
		StoreInfoResponse storeInfoResponse = storeInfoService.getStoreInfoService();
		ReprintReportDto reportDto = new ReprintReportDto();
		reportDto.setReportName("Reprint Receipt");
		reportDto.setStoreInfoResponse(storeInfoResponse);

		SequenceInfo sequenceInfo = sequenceInfoRepository.findByName("TRANSACTIONNO");

		List<InsertBill> insertBills = insertBillRepository.findByTransactionNumber(sequenceInfo.formatedValue());
		Map<String, InsertBillResponse> map = new HashMap<>();
		for (InsertBill bill : insertBills) {
			if (map.get(bill.getAmount()) != null) {
				InsertBillResponse insertBill = map.get(bill.getAmount());
				int count = insertBill.getCount() + 1;
				insertBill.setCount(count);
				map.put(bill.getAmount(), insertBill);
			} else {
				InsertBillResponse billResponse = new InsertBillResponse();
				billResponse.setAmount(bill.getAmount());
				billResponse.setCount(1);
				map.put(bill.getAmount(), billResponse);

			}

		}

		Collection<BillResponse> result = new ArrayList<BillResponse>();
		int count = 0;
		int sum = 0;
		for (Dollar dollar : Dollar.values()) {
			if (map.get(dollar.getDollar()) != null) {
				InsertBillResponse billResponse = map.get(dollar.getDollar());
				BillResponse response = new BillResponse();
				response.setCurrency(dollar.getDollar());
				response.setValue(billResponse.calculateSum(dollar.getValue()));
				response.setCount(billResponse.getCount());
				count = count + billResponse.getCount();
				sum = sum + response.getValue();
				result.add(response);
			}

		}
		BillResponse response = new BillResponse();
		response.setCurrency("All");
		response.setCount(count);
		response.setValue(sum);
		result.add(response);
		reportDto.setData(result);

		return reportDto;
	}

	public ReportDto testPrintReport() {
		ReportDto reportDto = new ReportDto();
		reportDto.setReportName("Test Print Receipt");
		reportDto.setStoreInfoResponse(storeInfoService.getStoreInfoService());
		return reportDto;
	}

	public InsertBillsReportDto insertBillsReport(String transactionNumber) {

		StoreInfoResponse storeInfoResponse = storeInfoService.getStoreInfoService();
		InsertBillsReportDto reportDto = new InsertBillsReportDto();
		reportDto.setStoreInfoResponse(storeInfoResponse);
		reportDto.setReportName("Insert Bills Receipt");
		reportDto.setTimeStamp(LocalDateTime.now().toString());

		List<InsertBill> insertBills = insertBillRepository.findByTransactionNumber(transactionNumber);
		Map<String, InsertBillResponse> map = new HashMap<String, InsertBillResponse>();
		for (InsertBill bill : insertBills) {
			if (map.get(bill.getAmount()) != null) {
				InsertBillResponse insertBill = map.get(bill.getAmount());
				int count = insertBill.getCount() + 1;
				insertBill.setCount(count);
				map.put(bill.getAmount(), insertBill);
			} else {
				InsertBillResponse billResponse = new InsertBillResponse();
				billResponse.setAmount(bill.getAmount());
				billResponse.setCount(1);
				map.put(bill.getAmount(), billResponse);

			}

		}
		Collection<BillResponse> result = new ArrayList<BillResponse>();
		int count = 0;
		int sum = 0;
		for (Dollar dollar : Dollar.values()) {
			if (map.get(dollar.getDollar()) != null) {
				InsertBillResponse billResponse = map.get(dollar.getDollar());
				BillResponse response = new BillResponse();
				response.setCurrency(dollar.getDollar());
				response.setValue(billResponse.calculateSum(dollar.getValue()));
				response.setCount(billResponse.getCount());
				count = count + billResponse.getCount();
				sum = sum + response.getValue();
				result.add(response);
			}

		}
		BillResponse response = new BillResponse();
		response.setCurrency("All");
		response.setCount(count);
		response.setValue(sum);
		result.add(response);
		reportDto.setData(result);

		return reportDto;

	}

	public EODReport endOfDayReport(Long userId) {

		StoreInfo storeInfo = new StoreInfo();
		Optional<UserInfo> optional = userInfoRepository.findById(userId);
		if (optional.isPresent()) {

			UserInfo dbUserInfo= optional.get();
		storeInfo =	dbUserInfo.getStoreInfo();
		}
		StoreInfoResponse storeInfoResponse = storeInfoService.getStoreInfoService(storeInfo.getStoreName());
		EODReport reportDto = new EODReport();
		reportDto.setTimeStamp(LocalDateTime.now().toString());
		reportDto.setStoreInfoResponse(storeInfoResponse);
		reportDto.setReportName("End of the Day Report");
		List<InsertBill> insertBills = insertBillRepository.findByCreatedOnAndUser_IdIn(LocalDate.now(), storeInfoResponse.getUserIds());

		Map<UserInfo, List<InsertBill>> userByBills = insertBills.stream()
				.collect(Collectors.groupingBy(InsertBill::getUser));

		List<EmployeeReportResponse> employeeData = new ArrayList<EmployeeReportResponse>();
		for (Map.Entry<UserInfo, List<InsertBill>> entry : userByBills.entrySet()) {
			EmployeeReportResponse er = new EmployeeReportResponse();
			Map<String, InsertBillResponse> map = new HashMap<String, InsertBillResponse>();
			for (InsertBill bill : entry.getValue()) {

				if (map.get(bill.getAmount()) != null) {
					InsertBillResponse insertBill = map.get(bill.getAmount());
					int count = insertBill.getCount() + 1;
					insertBill.setCount(count);
					map.put(bill.getAmount(), insertBill);
				} else {
					InsertBillResponse billResponse = new InsertBillResponse();
					billResponse.setAmount(bill.getAmount());
					billResponse.setCount(1);
					map.put(bill.getAmount(), billResponse);

				}

			}
			Collection<BillResponse> result = new ArrayList<BillResponse>();
			int count = 0;
			int sum = 0;
			for (Dollar dollar : Dollar.values()) {
				if (map.get(dollar.getDollar()) != null) {
					InsertBillResponse billResponse = map.get(dollar.getDollar());
					BillResponse response = new BillResponse();
					response.setCurrency(dollar.getDollar());
					response.setValue(billResponse.calculateSum(dollar.getValue()));
					response.setCount(billResponse.getCount());
					count = count + billResponse.getCount();
					sum = sum + response.getValue();
					result.add(response);
				}

			}
			BillResponse response = new BillResponse();
			response.setCurrency("All");
			response.setCount(count);
			response.setValue(sum);
			result.add(response);

			er.setName(entry.getKey().getUsername());
			er.setData(result);
			employeeData.add(er);
		}

		reportDto.setData(employeeData);

		return reportDto;
	}
	public  ByteArrayInputStream reportToExcel(Long userId, DateRangedto dateRangedto) throws IOException {
		
		UserInfo user = userInfoRepository.findById(userId).get();
		System.out.println(user.getStoreInfo().getStoreName());
		StoreInfoResponse storeInfoResponse = storeInfoService.getStoreInfoService(user.getStoreInfo().getStoreName());
		LocalDate stDate = LocalDate.parse(dateRangedto.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate endDate = LocalDate.parse(dateRangedto.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		if (stDate.isAfter(endDate)) {
			throw new RuntimeException("Start Date should be less than the End Date");
		}
		
		String[] columns = {"StoreName", "Store corpNo", "Serial No"};
		try(
		     Workbook workbook = new XSSFWorkbook();
		     ByteArrayOutputStream out = new ByteArrayOutputStream();
		     ){
		     Sheet sheet = workbook.createSheet("report");
		     Font headerFont = workbook.createFont();
		     headerFont.setBold(true);
		     
		     headerFont.setColor (IndexedColors.BLUE.getIndex());
		     CellStyle headerCellStyle = workbook.createCellStyle();
		     headerCellStyle.setFont(headerFont);
		     //Row for Header-->
		     
		     Row headerRow = sheet.createRow(0);
		     headerRow.setRowStyle(headerCellStyle);
		     //Header
		     for (int col=0; col<columns.length; col++) {
		    	 Cell cell = headerRow.createCell(col);
		    	 cell.setCellValue(columns[col]);
		     	}
		      Row detailsRow = sheet.createRow(1);
		    	  	Cell cell = detailsRow.createCell(0);
		    	  	String storeName = storeInfoResponse.getStoreName();
		    		  cell.setCellValue(storeName); 
		    		  cell = detailsRow.createCell(1);
		    		  cell.setCellValue(storeInfoResponse.getCorpStoreNo());
		    		  cell = detailsRow.createCell(2);
		    		  String serialNo = storeInfoResponse.getSerialNumber();
		    		  cell.setCellValue(serialNo);
		      Row newRow = sheet.createRow(2);
		       cell = newRow.createCell(0);
		      cell.setCellValue("Start date " + dateRangedto.getStartDate());
		      cell = newRow.createCell(1);
		      cell.setCellValue("End date " + dateRangedto.getEndDate());
		      
		      Row userRow = sheet.createRow(3);
		       cell = userRow.createCell(0);
		      cell.setCellValue("Employee Name  : " + user.getFirstName());
	    
		      LocalDate start = LocalDate.parse(dateRangedto.getStartDate());
		      LocalDate end = LocalDate.parse(dateRangedto.getEndDate());
		      List<LocalDate> totalDates = new ArrayList<>();
		      
		      while (!start.isAfter(end)) {
		          totalDates.add(start);
		          System.out.println(start);
		          start = start.plusDays(1);
		      }
		      int i = 4;
		      for(LocalDate date : totalDates) {
		    	  Row dataRow = sheet.createRow(i);
		    	  	i++;
		    	   cell = dataRow.createCell(0);
//		    	   String datee = date.toString();
		    	   DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MMM/dd/yyyy");
		    	   String date1 = date.format(myFormatObj);
		    	   System.out.println(date1);
		    	   cell.setCellValue(date1);
		    	   Row headingsRow = sheet.createRow(i);
		    	   headingsRow.setRowStyle(headerCellStyle);
		    	   headingsRow.createCell(0).setCellValue("Currency");
		    	   headingsRow.createCell(1).setCellValue("Count");
		    	   headingsRow.createCell(2).setCellValue("Value");
		    	   

		    	   List<InsertBill> insertBills = insertBillRepository.findByUser_IdAndCreatedOn(userId, date);
		    	   
		    	   for(InsertBill b : insertBills) {
		    		   
		    		   System.out.println(b.getAmount());
		    	   }
		    	   Set<String> distinctDenominations =  new HashSet<String>();
		    	   for(InsertBill bill : insertBills) {
		    		   distinctDenominations.add(bill.getAmount());
		    		   }
		    	  int rowNum = i+1;
		    	  int totalCount =0;
		    	  int sum = 0;
		    	   for(String a : distinctDenominations) {
		    		   System.out.println("distinct bill is " + a);
		    		   int count = 0;
		    		    
		    		   int product = 0;
		    		   for(InsertBill bill : insertBills) {
		    			  if(a.equals(bill.getAmount())) {
		    				  count++;
		    			  }
		    		   }
		    		   System.out.println("count is " +count + "and dollar is " + a);
		    		   Row amountRow = sheet.createRow(rowNum);
		    		   cell = amountRow.createCell(0);
		    		   cell.setCellValue(a);
		    		   cell = amountRow.createCell(1);
		    		   cell.setCellValue(count);
		    		   
		    		   cell = amountRow.createCell(2);
		    		   
		    		   
		    		   product = a.equals("$1")?1*count:a.equals("$2")?2*count:a.equals("$5")?5*count:a.equals("$10")?10*count:a.equals("$20")?20*count:
		    			   a.equals("$50")?50*count:a.equals("$100")?100*count:a.equals("$40")?40*count:1*count;
		    		   System.out.println("product is " + product);
		    		   cell.setCellValue(product);
		    		   System.out.println( " " + a + " " + count + " " + product );
		    		   rowNum++;
		    		   totalCount+= count;
		    		   sum+=product;
		    		   System.out.println("sum is " + sum);
		    	   
		    	   }
		    	   Row totalRow = sheet.createRow(rowNum);
		    	   totalRow.setRowStyle(headerCellStyle);
		    	   cell = totalRow.createCell(0);
		    	   cell.setCellValue("All");
		    	   cell = totalRow.createCell(1);
		    	   cell.setCellValue(totalCount);
		    	   cell = totalRow.createCell(2);
		    	   cell.setCellValue(sum);
		    	   i = rowNum+1;
		    	   
		      }
		    	 System.out.println("coming here");
		      workbook.write(out);
		      OutputStream fileOut = new FileOutputStream("E:\\reach your garden\\newReport.xlsx");
		      workbook.write(fileOut);
		      return new ByteArrayInputStream(out.toByteArray()); 
		 } 

	}

	public EmployeeReportDto employeeReportData(Long userId, DateRangedto dateRangedto) {

		LocalDate stDate = LocalDate.parse(dateRangedto.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate endDate = LocalDate.parse(dateRangedto.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		if (stDate.isAfter(endDate)) {
			throw new RuntimeException("Start Date should be less than the End Date");
		}

		List<InsertBill> insertBills = insertBillRepository.findByUser_IdAndCreatedOnBetween(userId, stDate, endDate);
		Map<LocalDate, List<InsertBill>> userByBills = insertBills.stream()
				.collect(Collectors.groupingBy(InsertBill::getCreatedOn));
		List<InsertBill> insertBills2 = insertBillRepository.findByUser_IdAndCreatedOn(userId, stDate);
		for(InsertBill b : insertBills2) {
			System.out.println(b.getAmount());
			
		}
		System.out.println("One day bills ");
		
		for(InsertBill a : insertBills) {
			System.out.println(a.getAmount());
		}
		 System.out.println("2 day bills");

		EmployeeReportDto employeeReport = new EmployeeReportDto();
		employeeReport.setReportName("Employee Report");
		StoreInfoResponse storeInfoResponse = storeInfoService.getStoreInfoService();
		employeeReport.setStoreInfoResponse(storeInfoResponse);
		employeeReport.setTimeStamp(LocalDateTime.now().toString());

		List<EmployeeReportResponse> employeeReportResponses = new ArrayList<EmployeeReportResponse>();
		for (Map.Entry<LocalDate, List<InsertBill>> entry : userByBills.entrySet()) {
			EmployeeReportResponse er = new EmployeeReportResponse();
			Map<String, InsertBillResponse> map = new HashMap<String, InsertBillResponse>();
			for (InsertBill bill : entry.getValue()) {

				if (map.get(bill.getAmount()) != null) {
					InsertBillResponse insertBill = map.get(bill.getAmount());
					int count = insertBill.getCount() + 1;
					insertBill.setCount(count);
					map.put(bill.getAmount(), insertBill);
				} else {
					InsertBillResponse billResponse = new InsertBillResponse();
					billResponse.setAmount(bill.getAmount());
					billResponse.setCount(1);
					map.put(bill.getAmount(), billResponse);

				}

			}
			Collection<BillResponse> result = new ArrayList<BillResponse>();
			int count = 0;
			int sum = 0;
			for (Dollar dollar : Dollar.values()) {
				if (map.get(dollar.getDollar()) != null) {
					InsertBillResponse billResponse = map.get(dollar.getDollar());
					BillResponse response = new BillResponse();
					response.setCurrency(dollar.getDollar());
					response.setValue(billResponse.calculateSum(dollar.getValue()));
					response.setCount(billResponse.getCount());
					count = count + billResponse.getCount();
					sum = sum + response.getValue();
					result.add(response);
				}

			}
			BillResponse response = new BillResponse();
			response.setCurrency("All");
			response.setCount(count);
			response.setValue(sum);
			result.add(response);
			er.setData(result);
			er.setName(entry.getKey().toString());
			employeeReportResponses.add(er);
		}

		employeeReport.setData(employeeReportResponses);

		return employeeReport;
	}
	public ManagerReportDto managerBillReport(DateRangedto dateRangedto) {

		LocalDate stDate = LocalDate.parse(dateRangedto.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate endDate = LocalDate.parse(dateRangedto.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		if (stDate.isAfter(endDate)) {
			throw new RuntimeException("Start Date should be less than the End Date");
		}

		List<InsertBill> insertBills = insertBillRepository.findByCreatedOnBetween(stDate, endDate);
		Map<UserInfo, List<InsertBill>> userByBills = insertBills.stream()
				.collect(Collectors.groupingBy(InsertBill::getUser));
		ManagerReportDto managerReport = new ManagerReportDto();
		List<EmployeeReportResponse> employeeReportResponses = new ArrayList<EmployeeReportResponse>();
		for (Map.Entry<UserInfo, List<InsertBill>> entry : userByBills.entrySet()) {
			EmployeeReportResponse er = new EmployeeReportResponse();
			Map<String, InsertBillResponse> map = new HashMap<String, InsertBillResponse>();
			for (InsertBill bill : entry.getValue()) {

				if (map.get(bill.getAmount()) != null) {
					InsertBillResponse insertBill = map.get(bill.getAmount());
					int count = insertBill.getCount() + 1;
					insertBill.setCount(count);
					map.put(bill.getAmount(), insertBill);
				} else {
					InsertBillResponse billResponse = new InsertBillResponse();
					billResponse.setAmount(bill.getAmount());
					billResponse.setCount(1);
					map.put(bill.getAmount(), billResponse);

				}

			}
			Collection<BillResponse> result = new ArrayList<BillResponse>();
			int count = 0;
			int sum = 0;
			for (Dollar dollar : Dollar.values()) {
				if (map.get(dollar.getDollar()) != null) {
					InsertBillResponse billResponse = map.get(dollar.getDollar());
					BillResponse response = new BillResponse();
					response.setCurrency(dollar.getDollar());
					response.setValue(billResponse.calculateSum(dollar.getValue()));
					response.setCount(billResponse.getCount());
					count = count + billResponse.getCount();
					sum = sum + response.getValue();
					result.add(response);
				}

			}
			BillResponse response = new BillResponse();
			response.setCurrency("All");
			response.setCount(count);
			response.setValue(sum);
			result.add(response);
			er.setData(result);
			er.setName(entry.getKey().getUsername());
			employeeReportResponses.add(er);
		}

		managerReport.setEmployeeReportResponses(employeeReportResponses);

		return managerReport;
	}

	

	public EmployeeReportDto managerReportData(Long userId, DateRangedto dateRangedto) {

		String stDate1 = DateUtil.convertToStringDateFormat(dateRangedto.getStartDate().substring(0, 10));
		stDate1 = stDate1 + " " + dateRangedto.getStartTime();

		String enDate1 = DateUtil.convertToStringDateFormat(dateRangedto.getEndDate().substring(0, 10));
		enDate1 = enDate1 + " " + dateRangedto.getEndTime();

		LocalDateTime stDate = LocalDateTime.parse(stDate1, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
		LocalDateTime endDate = LocalDateTime.parse(enDate1, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
		if (stDate.isAfter(endDate)) {
			throw new RuntimeException("Start Date should be less than the End Date");
		}

		List<InsertBill> insertBills = insertBillRepository.findByUser_IdAndDateTimeBetween(userId, stDate, endDate);
		Map<LocalDate, List<InsertBill>> userByBills = insertBills.stream()
				.collect(Collectors.groupingBy(InsertBill::getCreatedOn));

		EmployeeReportDto employeeReport = new EmployeeReportDto();
		employeeReport.setReportName("Manager Report");
		StoreInfoResponse storeInfoResponse = storeInfoService.getStoreInfoService();
		employeeReport.setStoreInfoResponse(storeInfoResponse);
		employeeReport.setTimeStamp(LocalDateTime.now().toString());

		List<EmployeeReportResponse> employeeReportResponses = new ArrayList<EmployeeReportResponse>();
		for (Map.Entry<LocalDate, List<InsertBill>> entry : userByBills.entrySet()) {
			EmployeeReportResponse er = new EmployeeReportResponse();
			Map<String, InsertBillResponse> map = new HashMap<String, InsertBillResponse>();
			for (InsertBill bill : entry.getValue()) {

				if (map.get(bill.getAmount()) != null) {
					InsertBillResponse insertBill = map.get(bill.getAmount());
					int count = insertBill.getCount() + 1;
					insertBill.setCount(count);
					map.put(bill.getAmount(), insertBill);
				} else {
					InsertBillResponse billResponse = new InsertBillResponse();
					billResponse.setAmount(bill.getAmount());
					billResponse.setCount(1);
					map.put(bill.getAmount(), billResponse);

				}

			}
			Collection<BillResponse> result = new ArrayList<BillResponse>();
			int count = 0;
			int sum = 0;
			for (Dollar dollar : Dollar.values()) {
				if (map.get(dollar.getDollar()) != null) {
					InsertBillResponse billResponse = map.get(dollar.getDollar());
					BillResponse response = new BillResponse();
					response.setCurrency(dollar.getDollar());
					response.setValue(billResponse.calculateSum(dollar.getValue()));
					response.setCount(billResponse.getCount());
					count = count + billResponse.getCount();
					sum = sum + response.getValue();
					result.add(response);
				}

			}
			BillResponse response = new BillResponse();
			response.setCurrency("All");
			response.setCount(count);
			response.setValue(sum);
			result.add(response);
			er.setData(result);
			er.setName(entry.getKey().toString());
			employeeReportResponses.add(er);
		}

		employeeReport.setData(employeeReportResponses);

		return employeeReport;
	}

	// EOD Report with Employee Data for Owner
	public void endOfDayReport1() {
		System.out.println("service method called");
		List<StoreInfoResponse> storesList = storeInfoService.findAssignedStores();
		List<StoreInfoResponse> selectedStoresList = new ArrayList<StoreInfoResponse>();
		if (storesList != null) {
			for (StoreInfoResponse storeInfoResponse : storesList) {

				if (storeInfoResponse.getEndTime().equalsIgnoreCase("06:00")) {

					selectedStoresList.add(storeInfoResponse);
				}
			}
		}

		for (StoreInfoResponse storeInfoResponse : storesList) {

			int totalCount = 0;
			int totalValue = 0;
			EODReport reportDto = new EODReport();
			reportDto.setTimeStamp(LocalDateTime.now().toString());
			reportDto.setStoreInfoResponse(storeInfoResponse);
			reportDto.setReportName("End of the Day Report");
			List<InsertBill> insertBills = insertBillRepository.findByCreatedOnAndUser_IdIn(LocalDate.now(),
					storeInfoResponse.getUserIds());

			Map<UserInfo, List<InsertBill>> userByBills = insertBills.stream()
					.collect(Collectors.groupingBy(InsertBill::getUser));

			List<EmployeeReportResponse> employeeData = new ArrayList<EmployeeReportResponse>();
			for (Map.Entry<UserInfo, List<InsertBill>> entry : userByBills.entrySet()) {
				EmployeeReportResponse er = new EmployeeReportResponse();
				Map<String, InsertBillResponse> map = new HashMap<String, InsertBillResponse>();
				for (InsertBill bill : entry.getValue()) {

					if (map.get(bill.getAmount()) != null) {
						InsertBillResponse insertBill = map.get(bill.getAmount());
						int count = insertBill.getCount() + 1;
						insertBill.setCount(count);
						map.put(bill.getAmount(), insertBill);
					} else {
						InsertBillResponse billResponse = new InsertBillResponse();
						billResponse.setAmount(bill.getAmount());
						billResponse.setCount(1);
						map.put(bill.getAmount(), billResponse);

					}

				}
				Collection<BillResponse> result = new ArrayList<BillResponse>();
				int count = 0;
				int sum = 0;
				for (Dollar dollar : Dollar.values()) {
					if (map.get(dollar.getDollar()) != null) {
						InsertBillResponse billResponse = map.get(dollar.getDollar());
						BillResponse response = new BillResponse();
						response.setCurrency(dollar.getDollar());
						response.setValue(billResponse.calculateSum(dollar.getValue()));
						response.setCount(billResponse.getCount());
						count = count + billResponse.getCount();
						sum = sum + response.getValue();
						result.add(response);
					}

				}
				BillResponse response = new BillResponse();
				response.setCurrency("All");
				response.setCount(count);
				response.setValue(sum);
				result.add(response);

				totalCount = totalCount + count;
				totalValue = totalValue + sum;
				er.setName(entry.getKey().getUsername());
				er.setData(result);
				employeeData.add(er);
			}

			reportDto.setData(employeeData);
			reportDto.setTotalCount(totalCount);
			reportDto.setTotalValue(totalValue);

			emailTemplate.createEndOfDayMail(reportDto);
		}

	}



//EOD Report with Employee Data for Bank
	public void endOfDayReport2() {
		System.out.println("Bank service method called");
		List<StoreInfoResponse> storesList = storeInfoService.findAssignedStores();
		List<StoreInfoResponse> selectedStoresList = new ArrayList<StoreInfoResponse>();
		if (storesList != null) {
			for (StoreInfoResponse storeInfoResponse : storesList) {

				if (storeInfoResponse.getEndTime().equalsIgnoreCase("06:00")) {

					selectedStoresList.add(storeInfoResponse);
				}
			}
		}

		for (StoreInfoResponse storeInfoResponse : storesList) {

			int totalCount = 0;
			int totalValue = 0;
			EODReport reportDto = new EODReport();
			reportDto.setTimeStamp(LocalDateTime.now().toString());
			reportDto.setStoreInfoResponse(storeInfoResponse);
			reportDto.setReportName("End of the Day Bank Report");
			List<InsertBill> insertBills = insertBillRepository.findByCreatedOnAndUser_IdIn(LocalDate.now(),
					storeInfoResponse.getUserIds());

			Map<UserInfo, List<InsertBill>> userByBills = insertBills.stream()
					.collect(Collectors.groupingBy(InsertBill::getUser));

			List<EmployeeReportResponse> employeeData = new ArrayList<EmployeeReportResponse>();
			for (Map.Entry<UserInfo, List<InsertBill>> entry : userByBills.entrySet()) {
				EmployeeReportResponse er = new EmployeeReportResponse();
				Map<String, InsertBillResponse> map = new HashMap<String, InsertBillResponse>();
				for (InsertBill bill : entry.getValue()) {

					if (map.get(bill.getAmount()) != null) {
						InsertBillResponse insertBill = map.get(bill.getAmount());
						int count = insertBill.getCount() + 1;
						insertBill.setCount(count);
						map.put(bill.getAmount(), insertBill);
					} else {
						InsertBillResponse billResponse = new InsertBillResponse();
						billResponse.setAmount(bill.getAmount());
						billResponse.setCount(1);
						map.put(bill.getAmount(), billResponse);

					}

				}
				Collection<BillResponse> result = new ArrayList<BillResponse>();
				int count = 0;
				int sum = 0;
				for (Dollar dollar : Dollar.values()) {
					if (map.get(dollar.getDollar()) != null) {
						InsertBillResponse billResponse = map.get(dollar.getDollar());
						BillResponse response = new BillResponse();
						response.setCurrency(dollar.getDollar());
						response.setValue(billResponse.calculateSum(dollar.getValue()));
						response.setCount(billResponse.getCount());
						count = count + billResponse.getCount();
						sum = sum + response.getValue();
						result.add(response);
					}

				}
				BillResponse response = new BillResponse();
				response.setCurrency("All");
				response.setCount(count);
				response.setValue(sum);
				result.add(response);

				totalCount = totalCount + count;
				totalValue = totalValue + sum;
				er.setName(entry.getKey().getUsername());
				er.setData(result);
				employeeData.add(er);
			}

			reportDto.setData(employeeData);
			reportDto.setTotalCount(totalCount);
			reportDto.setTotalValue(totalValue);

			emailTemplate.createEndOfDayBankMail(reportDto);
		}

	}

}



