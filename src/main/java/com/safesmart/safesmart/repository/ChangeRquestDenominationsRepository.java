package com.safesmart.safesmart.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.safesmart.safesmart.model.ChangeValetDenominations;

@Repository
public interface ChangeRquestDenominationsRepository
		extends PagingAndSortingRepository<ChangeValetDenominations, Long> {

	List<ChangeValetDenominations> findByValetDenominations_Id(Long valetDenominationsId);
}
