package com.safesmart.safesmart.repository;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.safesmart.safesmart.dto.RoleConunt;
import com.safesmart.safesmart.dto.StoreInfoResponse;
import com.safesmart.safesmart.model.StoreInfo;
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
    
	@Query("SELECT u FROM UserInfo  u where storeInfo = :id")
	List<UserInfo> getAllUsers(Long id);
	
//    @Query( "SELECT  r.id,r.name,count(*) FROM safemart.user_info uf LEFT JOIN role r ON uf.role_id = r.id where store_info_id=storeid group by role_id")
//	RoleConunt getAllRolesCount( Long storeid);
//	
	
	
	
   
}
