package com.safesmart.safesmart.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.safesmart.safesmart.model.Printer;

@Repository
public interface PrinterRepository extends PagingAndSortingRepository<Printer, Long>{

	
	Printer findByPrinterName(String printerName);
}
