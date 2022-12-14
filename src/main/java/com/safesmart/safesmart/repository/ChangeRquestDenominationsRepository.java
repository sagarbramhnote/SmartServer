package com.safesmart.safesmart.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.safesmart.safesmart.model.ChangeValetDenominations;
import com.safesmart.safesmart.model.UserInfo;
import com.safesmart.safesmart.model.ValetDenominations;

@Repository
public interface ChangeRquestDenominationsRepository
		extends PagingAndSortingRepository<ChangeValetDenominations, Long> {

	List<ChangeValetDenominations> findByValetDenominations_Id(Long valetDenominationsId);
	
	ChangeValetDenominations findByType(String type);
	
	List<ChangeValetDenominations> findBycreatedByAndCreatedBetweenAndValetDenominations(UserInfo user,LocalDateTime stDate,LocalDateTime endDate,ValetDenominations valetDenominations);
	
}
