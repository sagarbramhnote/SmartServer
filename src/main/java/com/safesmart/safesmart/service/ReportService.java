package com.safesmart.safesmart.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.BorderStyle;
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
	
	//Exporting EOD report to Excel
	public  ByteArrayInputStream EODReportToExcel(String storeName, boolean toDay) throws IOException {
		
		// the path variable boolean toDay indicates if the check box " TODAY "in the EOD reports is true or false 
		StoreInfoResponse storeInfoResponse = storeInfoService.getStoreInfoService(storeName);
		List<Long> userIds = storeInfoResponse.getUserIds();
		String stTime = storeInfoResponse.getStartTime();
		String endTimes = storeInfoResponse.getEndTime();
		LocalTime startTime = LocalTime.parse(stTime);
		LocalTime endTime = LocalTime.parse(endTimes);
		LocalTime now = LocalTime.now();
		
		LocalDateTime startDateTime = null;
		LocalDateTime endDateTime = null;
		LocalDate startDate = null;
		LocalDate endDate = null;
		// if check box Today is not checked then endDateTime will be assigned as certain conditions based on current time and Store default 
		// Open time and Close time 
	
		if(!toDay){
			
		/* now.compareTo(endTime) returns positive if now is greater than end time 
		 returns zero if equal 
		returns negative if now is less than end time */
		int diff = now.compareTo(endTime); 
		
		/*Logic for if report is generated after end time that current date report will be generated if 10:00 AM  is end time and report is generated at 10:01 AM then report will be from yesterday 10:00Am to todat 10 AM
		if report is generated at 09:59 AM then report will be from day before yesterday 10:00 AM to yesterday 10:00 AM */
		
		 endDate = (diff>0)|| (diff==0)?LocalDate.now():LocalDate.now().minusDays(1);
		
			endDateTime = endTime.atDate(endDate);
		}else {
			 	endDate = LocalDate.now();
				endDateTime = LocalTime.now().atDate(endDate);
		}
		//Checking = difference between start time and end time of a Store
				long hours = ChronoUnit.HOURS.between(startTime, endTime);
				long minutes
		        = ChronoUnit.MINUTES.between(startTime, endTime) % 60;
				long seconds
	            = ChronoUnit.SECONDS.between(startTime, endTime) % 60;
			// To determine the start date is before day or same day based on the store start time and end time
					if(hours<0 || minutes <0 || seconds <0 || (hours ==0 && minutes ==0 && seconds ==0)) {
					 startDate = endDate.minusDays(1);
					}else {
						startDate = endDate;
					}
					System.out.println(" Start Date is " + startDate + " End date is " + endDate);
					startDateTime = startTime.atDate(startDate);
			
		System.out.println(userIds);
		String[] columns = {"Store Name", "Store corp No", "Serial No"}; 
		try(
		     Workbook workbook = new XSSFWorkbook();
		     ByteArrayOutputStream out = new ByteArrayOutputStream();
		     ){
		     Sheet sheet = workbook.createSheet("report");
		     Font headerFont = workbook.createFont();
		     headerFont.setBold(true);
		     headerFont.setColor (IndexedColors.BLACK.getIndex());
		     
		     BorderStyle bS = BorderStyle.THICK;
		     CellStyle fullBold = workbook.createCellStyle();
		     fullBold.setBorderTop(bS);
		     fullBold.setBorderLeft(bS);
		     fullBold.setBorderBottom(bS);
		     fullBold.setBorderRight(bS);
		     fullBold.setFont(headerFont);
		     CellStyle full = workbook.createCellStyle();
		     full.setBorderBottom(bS);
		     full.setBorderLeft(bS);
		     full.setBorderRight(bS);
		     full.setBorderTop(bS);
		     CellStyle leftRight = workbook.createCellStyle();
		     leftRight.setBorderLeft(bS);
		     
		     
		     
		     int i = 0;
		     Row headerRow = sheet.createRow(i);
		     i++;
		     
		    	 Cell cell = headerRow.createCell(0);
		    	 cell.setCellValue(columns[0]);
		    	 cell.setCellStyle(fullBold);
		    	 
		    	 cell = headerRow.createCell(1);
		    	 cell.setCellValue(columns[1]);
		    	 cell.setCellStyle(fullBold);
		    	 
		    	 cell = headerRow.createCell(2);
		    	 cell.setCellValue(columns[2]);
		    	 cell.setCellStyle(fullBold);
		     	
		     //Row for printing Store details 
		  
		      Row detailsRow = sheet.createRow(i);
		      i++;
		    		String serialNo = storeInfoResponse.getSerialNumber();
		    		cell = detailsRow.createCell(0);
		    		cell.setCellValue(storeName);
		    		cell.setCellStyle(full);

		    		 cell = detailsRow.createCell(1);
		    		 cell.setCellValue(storeInfoResponse.getCorpStoreNo());
		    		 cell.setCellStyle(full);
		    		 
		    		 cell = detailsRow.createCell(2);
		    		 cell.setCellValue(serialNo);
		    		 cell.setCellStyle(full);
		    		 
		    // Row for printing start date and end date 
		      Row datesRow = sheet.createRow(i);
		      i+=2;
		      cell = datesRow.createCell(0);
		      cell.setCellValue("From Date :" + startDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		      cell.setCellStyle(full);
		      cell = datesRow.createCell(1);
		      cell.setCellStyle(full);
		      
		      cell = datesRow.createCell(2);
		      cell.setCellValue("To Date :" + endDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		      cell.setCellStyle(full);
		      
		      int grandTotal =0;
		      int grandCount =0;
		     for(Long userId : userIds) {
		      List<InsertBill> insertBills = insertBillRepository.findByUser_IdAndDateTimeBetween(userId, startDateTime, endDateTime);
		      if(!insertBills.isEmpty()) {
		    	  
		    	  UserInfo user = userInfoRepository.findById(userId).get();
		    	  // Printing employee name 
		    	  Row userRow = sheet.createRow(i);
		    	  i++;
		    	  cell = userRow.createCell(0);
		    	  cell.setCellValue("Name");
		    	  cell.setCellStyle(fullBold);
		    	  cell = userRow.createCell(1);
		    	  cell.setCellValue(user.getFirstName() + " " + user.getLastName());
		    	  cell.setCellStyle(full);
		    	  //Row for printing headings 
		    	  Row headingsRow = sheet.createRow(i);
		    	  cell = headingsRow.createCell(0);
		    	  cell.setCellValue("Currency");
		    	  cell.setCellStyle(fullBold);
    	   
		    	  cell = headingsRow.createCell(1);
		    	  cell.setCellValue("Count");
		    	  cell.setCellStyle(fullBold);
    	   
		    	  cell = headingsRow.createCell(2);
		    	  cell.setCellValue("Value");
		    	  cell.setCellStyle(fullBold);
    	   
    	   Set<String> distinctDenominations =  new HashSet<String>();
    	   for(InsertBill bill : insertBills) {
    		   distinctDenominations.add(bill.getAmount());
    		   }
    	  i++;
    	  int totalCount =0;
    	  int sum = 0;
    	  //Adding Distinct denominations in to a set like $1,$2,...
    	   for(String a : distinctDenominations) {
    		   int count = 0;
    		   int product = 0;
    		   //checking number of notes of same denomination present in the current date bill
    		   for(InsertBill bill : insertBills) {
    			  if(a.equals(bill.getAmount())) {
    				  count++;
    			  }
    		   }
    		   //Row for printing values i.e., denomination ($1,$2,.....) , NO of notes (Count), Value ( denominations * Count)
    		   Row amountRow = sheet.createRow(i);
    		   cell = amountRow.createCell(0);
    		   cell.setCellValue(a);
    		   cell.setCellStyle(leftRight);
    		   
    		   amountRow.createCell(1).setCellValue(count);
    		   
    		   product = a.equals("$1")?1*count:a.equals("$2")?2*count:a.equals("$5")?5*count:a.equals("$10")?10*count:a.equals("$20")?20*count:
    			   a.equals("$50")?50*count:a.equals("$100")?100*count:1*count;
    		   
    		   
    		   cell = amountRow.createCell(2);
    		   cell.setCellValue("$" + Long.toString(product));
    		   cell.setCellStyle(leftRight);
    		   
    		   i++;
    		   totalCount+= count;
    		   sum+=product;
    	   
    	   	}
    	   // Row for printing current day total
    	   Row totalRow = sheet.createRow(i);
    	   
    	   cell = totalRow.createCell(0);
    	   cell.setCellValue("All");
    	   cell.setCellStyle(fullBold);
    	   
    	   cell = totalRow.createCell(1);
    	   cell.setCellValue(totalCount);
    	   cell.setCellStyle(full);
    	   
    	   cell = totalRow.createCell(2);
    	   cell.setCellValue("$" + Long.toString(sum));
    	   cell.setCellStyle(full);
    	   
    	   grandCount+= totalCount; 
    	    
    	   grandTotal +=sum;
    	   i+=2;
    	   
    	   }
		      
		      
		  }
		     // Row for printing grand total
		      Row grandTotalRow = sheet.createRow(i);
		      
		      cell = grandTotalRow.createCell(0);
		      cell.setCellValue("Total Bills Per Day");
		      cell.setCellStyle(fullBold);
		      
		     cell =  grandTotalRow.createCell(1);
		     cell.setCellValue(grandCount);
		     cell.setCellStyle(fullBold);
		      
		      cell = grandTotalRow.createCell(2);
		      cell.setCellValue("$" + Long.toString(grandTotal));
		      cell.setCellStyle(fullBold);
		      workbook.write(out);
		      if(!toDay) {
		      OutputStream fileOut = new FileOutputStream("D:\\newEODReport.xlsx");
		      workbook.write(fileOut);
		      }else {
		    	  OutputStream fileOut = new FileOutputStream("D:\\TodayEODReport.xlsx");
			      workbook.write(fileOut);
		      }
		
		
		
		return new ByteArrayInputStream(out.toByteArray());
		}
	}
	
	//Exporting Employees Reports to Excel 
	public  ByteArrayInputStream reportToExcel(Long userId, DateRangedto dateRangedto) throws IOException {
		
		UserInfo user = userInfoRepository.findById(userId).get();
		System.out.println(user.getStoreInfo().getStoreName());
		StoreInfoResponse storeInfoResponse = storeInfoService.getStoreInfoService(user.getStoreInfo().getStoreName());
		LocalDate stDate = LocalDate.parse(dateRangedto.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate endDate = LocalDate.parse(dateRangedto.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		if (stDate.isAfter(endDate)) {
			throw new RuntimeException("Start Date should be less than the End Date");
		}
		
		String[] columns = {"Store Name", "Store corp No", "Serial No"}; 
		try(
		     Workbook workbook = new XSSFWorkbook();
		     ByteArrayOutputStream out = new ByteArrayOutputStream();
		     ){
		     Sheet sheet = workbook.createSheet("report");
		     Font headerFont = workbook.createFont();
		     headerFont.setBold(true);
		     
		     headerFont.setColor (IndexedColors.BLACK.getIndex());
		     
		     BorderStyle bS = BorderStyle.THICK;
		     CellStyle fullBold = workbook.createCellStyle();
		     fullBold.setBorderTop(bS);
		     fullBold.setBorderLeft(bS);
		     fullBold.setBorderBottom(bS);
		     fullBold.setBorderRight(bS);
		     fullBold.setFont(headerFont);
		     CellStyle full = workbook.createCellStyle();
		     full.setBorderBottom(bS);
		     full.setBorderLeft(bS);
		     full.setBorderRight(bS);
		     full.setBorderTop(bS);
		     CellStyle leftRight = workbook.createCellStyle();
		     leftRight.setBorderLeft(bS);

		     Row headerRow = sheet.createRow(0);
		     for (int col=0; col<columns.length; col++) {
		    	 Cell cell = headerRow.createCell(col);
		    	 cell.setCellValue(columns[col]);
		    	 cell.setCellStyle(fullBold);
		     	}
		     //Row for printing Store details 
		      Row detailsRow = sheet.createRow(1);
		    	  	String storeName = storeInfoResponse.getStoreName();
		    		String serialNo = storeInfoResponse.getSerialNumber();
		    		Cell cell = detailsRow.createCell(0);
		    		cell.setCellValue(storeName);
		    		cell.setCellStyle(full);

		    		 cell = detailsRow.createCell(1);
		    		 cell.setCellValue(storeInfoResponse.getCorpStoreNo());
		    		 cell.setCellStyle(full);
		    		 
		    		 cell = detailsRow.createCell(2);
		    		 cell.setCellValue(serialNo);
		    		 cell.setCellStyle(full);
		    		 
		    // Row for printing start date and end date 
		      Row datesRow = sheet.createRow(2);
		      
		      cell = datesRow.createCell(0);
		      cell.setCellValue("From Date :" + stDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		      cell.setCellStyle(full);
		      
		      cell = datesRow.createCell(2);
		      cell.setCellValue("To Date :" + endDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		      cell.setCellStyle(full);
		     
		      Row userRow = sheet.createRow(3);
		      cell = userRow.createCell(0);
		      cell.setCellValue("Employee Name : ");
		      cell.setCellStyle(fullBold);
		      cell = userRow.createCell(1);
		      cell.setCellValue(user.getFirstName()+" " + user.getLastName());
		      cell.setCellStyle(full);
		      List<LocalDate> totalDates = new ArrayList<>();
		      // Adding in between dates into a List
		      while (!stDate.isAfter(endDate)) {
		      
		    	  totalDates.add(stDate);
		          stDate = stDate.plusDays(1);
		      }
		      int grandTotal = 0;
		      int i = 5;
		      //Iterating date by date present in the list
		      for(LocalDate date : totalDates) {
		    	  
		    	   List<InsertBill> insertBills = insertBillRepository.findByUser_IdAndCreatedOn(userId, date);
		    	   //Checking if any bills present in the current date if there are no bills go and check next date
		    	   if(!insertBills.isEmpty()) {
		    		   //Row for printing date 
		    		   Row dateRow = sheet.createRow(i);
			    	   i++;
		    	   cell = dateRow.createCell(0);
		    	   cell.setCellValue("Date: "+date.format(DateTimeFormatter.ofPattern("MMM/dd/yyyy")));
		    	   cell.setCellStyle(fullBold);
		    	   //Row for printing headings 
		    	   Row headingsRow = sheet.createRow(i);
		    	   cell = headingsRow.createCell(0);
		    	   cell.setCellValue("Currency");
		    	   cell.setCellStyle(fullBold);
		    	   
		    	   cell = headingsRow.createCell(1);
		    	   cell.setCellValue("Count");
		    	   cell.setCellStyle(fullBold);
		    	   
		    	   cell = headingsRow.createCell(2);
		    	   cell.setCellValue("Value");
		    	   cell.setCellStyle(fullBold);
		    	   
		    	   Set<String> distinctDenominations =  new HashSet<String>();
		    	   for(InsertBill bill : insertBills) {
		    		   distinctDenominations.add(bill.getAmount());
		    		   }
		    	  i++;
		    	  int totalCount =0;
		    	  int sum = 0;
		    	  //Adding Distinct denominations in to a set like $1,$2,...
		    	   for(String a : distinctDenominations) {
		    		   int count = 0;
		    		   int product = 0;
		    		   //checking number of notes of same denomination present in the current date bill
		    		   for(InsertBill bill : insertBills) {
		    			  if(a.equals(bill.getAmount())) {
		    				  count++;
		    			  }
		    		   }
		    		   //Row for printing values i.e., denomination ($1,$2,.....) , NO of notes (Count), Value ( denominations * Count)
		    		   Row amountRow = sheet.createRow(i);
		    		   
		    		   cell = amountRow.createCell(0);
		    		   cell.setCellValue(a);
		    		   cell.setCellStyle(leftRight);
		    		   amountRow.createCell(1).setCellValue(count);
		    		   
		    		   product = a.equals("$1")?1*count:a.equals("$2")?2*count:a.equals("$5")?5*count:a.equals("$10")?10*count:a.equals("$20")?20*count:
		    			   a.equals("$50")?50*count:a.equals("$100")?100*count:1*count;
		    		   
		    		   
		    		  cell =  amountRow.createCell(2);
		    		  cell.setCellValue("$" + Long.toString(product));
		    		  cell.setCellStyle(leftRight);
		    		   
		    		   i++;
		    		   totalCount+= count;
		    		   sum+=product;
		    	   
		    	   	}
		    	   // Row for printing current day total
		    	   Row totalRow = sheet.createRow(i);
		    	   
		    	   cell = totalRow.createCell(0);
		    	   cell.setCellValue("All");
		    	   cell.setCellStyle(full);
		    	   
		    	   cell = totalRow.createCell(1);
		    	   cell.setCellValue(totalCount);
		    	   cell.setCellStyle(full);
		    	   
		    	   cell = totalRow.createCell(2);
		    	   cell.setCellValue("$" + Long.toString(sum));
		    	   cell.setCellStyle(full);
		    	    
		    	   i+=2;
		    	   grandTotal+=sum;
		    	   
		    	   }
		      } 
		      // Row for printing grand total
		      Row grandTotalRow = sheet.createRow(i);
		      
		      cell = grandTotalRow.createCell(0);
		      cell.setCellValue("Total Bills ");
		      cell.setCellStyle(fullBold);
 		      cell = grandTotalRow.createCell(1);
 		      cell.setCellValue(" " );
 		      cell.setCellStyle(full);
		      cell = grandTotalRow.createCell(2);
		      cell.setCellValue(grandTotal);
		      cell.setCellStyle(full);
		      workbook.write(out);
		      OutputStream fileOut = new FileOutputStream("D:\\newReport.xlsx");
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



