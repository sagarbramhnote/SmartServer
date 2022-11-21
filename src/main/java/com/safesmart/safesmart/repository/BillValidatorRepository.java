package com.safesmart.safesmart.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.safesmart.safesmart.model.BillValidator;

@Repository
public interface BillValidatorRepository extends PagingAndSortingRepository<BillValidator, Long> {

	BillValidator findByBillAcceptorName(String billAcceptorName);
}
