package com.safesmart.safesmart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.safesmart.safesmart.model.ChangeRequest;

@Repository
public interface ChangeRequestRepository
		extends PagingAndSortingRepository<ChangeRequest, Long> {

	Optional<ChangeRequest> findById(Long id);
	
	List<ChangeRequest> findAll();
	
	ChangeRequest findByTypeAndOrderStatus(String type, String orderStatus);
}
