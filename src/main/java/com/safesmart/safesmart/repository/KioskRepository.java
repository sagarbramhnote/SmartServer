package com.safesmart.safesmart.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.safesmart.safesmart.model.Kiosk;


@Repository
public interface KioskRepository extends PagingAndSortingRepository<Kiosk, Long> {

	Kiosk findByKioskName(String kioskName);
	
}
