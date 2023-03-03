package com.safesmart.safesmart.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.safesmart.safesmart.model.LocksInfo;
import com.safesmart.safesmart.model.UserInfo;
import com.safesmart.safesmart.repository.LockInfoRepository;
import com.safesmart.safesmart.repository.UserInfoRepository;

@Service
@Transactional
public class LockInfoService {

	@Autowired
	private LockInfoRepository lockInfoRepository;
	
	@Autowired
	private UserInfoRepository userInfoRepository;

	public void add(Long userId, String type) {
	
		UserInfo user = userInfoRepository.findById(userId).get();
		
		LocksInfo lockinfo =new LocksInfo();
		
		lockinfo.setUsers(user);
		lockinfo.setLockType(type);
		lockinfo.setLockTime(LocalDateTime.now());
		lockinfo.setLockStatus("Open");
		
		lockInfoRepository.save(lockinfo);
		
	}	

}
