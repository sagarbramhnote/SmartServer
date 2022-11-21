package com.safesmart.safesmart.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.safesmart.safesmart.common.CommonException;
import com.safesmart.safesmart.common.CommonExceptionMessage;
import com.safesmart.safesmart.dto.BillValidatorResponse;
import com.safesmart.safesmart.dto.LocksRequest;
import com.safesmart.safesmart.dto.PrinterRequest;
import com.safesmart.safesmart.dto.PrinterResponse;
import com.safesmart.safesmart.model.BillValidator;
import com.safesmart.safesmart.model.Locks;
import com.safesmart.safesmart.model.Printer;
import com.safesmart.safesmart.repository.PrinterRepository;

@Service
@Transactional
public class PrinterService {

	
	@Autowired
	private PrinterRepository printerRepository;
	
	public void add(PrinterRequest printerRequest) {

		Printer printer = printerRepository.findByPrinterName(printerRequest.getPrinterName());
		if (printer != null) {
			throw CommonException.CreateException(CommonExceptionMessage.ALREADY_EXISTS, "PrinterName");
		}
	

		printer = new Printer();
		printer.setPrinterNo(printerRequest.getPrinterNo());
		printer.setPrinterName(printerRequest.getPrinterName());
		printer.setBrandName(printerRequest.getBrandName());
		printer.setModelName(printerRequest.getModelName());
		printer.setMachineType(printerRequest.getMachineType());
		printer.setPrintCapacity(printerRequest.getPrintCapacity());
	
		printer.setActive(true);

	
		printerRepository.save(printer);
	}
	
	public List<PrinterResponse> findAllUser() {
		// TODO Auto-generated method stub
		List<Printer> printers = (List<Printer>) printerRepository.findAll();

		List<PrinterResponse> printerResponses = new ArrayList<PrinterResponse>();
		for (Printer printer : printers) {
			printerResponses.add(new PrinterResponse(printer.getId(),printer.getPrinterNo(), printer.getPrinterName(), printer.getBrandName(),
					printer.getModelName(), printer.getMachineType(), printer.getPrintCapacity(), printer.isActive()));
		}
		return printerResponses;
	}
	
	public void deleteByPrinter(Long Id) {
		printerRepository.deleteById(Id);
	}
	
	public void updatePrinter(PrinterRequest printerRequest) {


		Printer printer = printerRepository.findById(printerRequest.getId()).get();

		printer.setPrinterNo(printerRequest.getPrinterNo());
		printer.setPrinterName(printerRequest.getPrinterName());
		printer.setBrandName(printerRequest.getBrandName());
		printer.setModelName(printerRequest.getModelName());
		printer.setMachineType(printerRequest.getMachineType());
		printer.setPrintCapacity(printerRequest.getPrintCapacity());
	
		printer.setActive(true);

	
		printerRepository.save(printer);

	}
}
