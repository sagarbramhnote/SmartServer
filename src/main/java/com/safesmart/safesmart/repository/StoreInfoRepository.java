package com.safesmart.safesmart.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.safesmart.safesmart.model.StoreInfo;
@Repository
public interface StoreInfoRepository extends PagingAndSortingRepository<StoreInfo, Long> {

	StoreInfo findByStoreName(String string);
	
	List<StoreInfo> findByConfigured(boolean configured);
	
	
	StoreInfo findByStoreNameAndConfigured(String string, boolean configured);
	
	
	StoreInfo findByCorpStoreNo(String string);
	
	StoreInfo findBySerialNumber(String serialNumber);
	
	StoreInfo findByAccountNumber(String accountNumber);
	

}
