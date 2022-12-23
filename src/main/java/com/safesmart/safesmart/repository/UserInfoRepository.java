package com.safesmart.safesmart.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.safesmart.safesmart.model.UserInfo;

@Repository
public interface UserInfoRepository extends PagingAndSortingRepository<UserInfo, Long> {

	UserInfo findByPassword(String password);

	UserInfo findByUsername(String username);

	UserInfo findByUsernameAndPassword(String username, String password);

	List<UserInfo> findByRole_Name(String role);

	List<UserInfo> findByRole_NameIn(List<String> roles);
	UserInfo findByMobile(String mobile);
	UserInfo findByEmail(String email);
	
	List<UserInfo> findByStoreInfo_Id(Long id);

	
	
}
