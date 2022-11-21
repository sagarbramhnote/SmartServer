package com.safesmart.safesmart.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

	public EmployeeReportDto employeeReportData(Long userId, DateRangedto dateRangedto) {

		LocalDate stDate = LocalDate.parse(dateRangedto.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate endDate = LocalDate.parse(dateRangedto.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		if (stDate.isAfter(endDate)) {
			throw new RuntimeException("Start Date should be less than the End Date");
		}

		List<InsertBill> insertBills = insertBillRepository.findByUser_IdAndCreatedOnBetween(userId, stDate, endDate);
		Map<LocalDate, List<InsertBill>> userByBills = insertBills.stream()
				.collect(Collectors.groupingBy(InsertBill::getCreatedOn));

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



