package com.safesmart.safesmart.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.safesmart.safesmart.builder.StoreInfoBuilder;
import com.safesmart.safesmart.common.CommonException;
import com.safesmart.safesmart.common.CommonExceptionMessage;
import com.safesmart.safesmart.dto.StoreInfoRequest;
import com.safesmart.safesmart.dto.StoreInfoResponse;
import com.safesmart.safesmart.dto.UserInfoRequest;
import com.safesmart.safesmart.model.Locks;
import com.safesmart.safesmart.model.Printer;
import com.safesmart.safesmart.model.StoreInfo;
import com.safesmart.safesmart.model.UserInfo;
import com.safesmart.safesmart.repository.LocksRepository;
import com.safesmart.safesmart.repository.StoreInfoRepository;
import com.safesmart.safesmart.repository.UserInfoRepository;

@Service
@Transactional
@CrossOrigin(origins = "*") 
public class StoreInfoService {

	@Autowired
	private StoreInfoRepository storeInfoRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private LocksRepository locksRepository;

	@Autowired
	private StoreInfoBuilder storeInfoBuilder;

	public StoreInfoResponse getStoreInfoService() {
		StoreInfo storeInfo = storeInfoRepository.findByStoreName("XYZ");
		return new StoreInfoResponse(storeInfo.getId(), storeInfo.getStoreName(), storeInfo.getCorpStoreNo(),
				storeInfo.getSerialNumber());
	}

	public StoreInfoResponse getStoreInfoService(String storeName) {
		StoreInfo storeInfo = storeInfoRepository.findByStoreNameAndConfigured(storeName, true);
		StoreInfoResponse storeInfoResponse = storeInfoBuilder.toDto(storeInfo);
		return storeInfoResponse;
	}

	public void addStore(StoreInfoRequest storeInfoRequest) {
		StoreInfo storeInfo = storeInfoRepository.findByStoreName(storeInfoRequest.getStoreName());
		if (storeInfo != null) {
			throw CommonException.CreateException(CommonExceptionMessage.ALREADY_EXISTS,
					storeInfoRequest.getStoreName());
		}
		storeInfo = storeInfoBuilder.toModel(storeInfoRequest);
		storeInfoRepository.save(storeInfo);

	}

	public List<StoreInfoResponse> findAll() {
		List<StoreInfo> storeInfos = (List<StoreInfo>) storeInfoRepository.findAll();

		return storeInfoBuilder.toDtoList(storeInfos);
	}

	public StoreInfoResponse findByStoreName(String storeName) {

		StoreInfo storeInfo = storeInfoRepository.findByStoreName(storeName);
		if (storeInfo == null) {
			throw CommonException.CreateException(CommonExceptionMessage.NOTFOUND, storeName);
		}

		return storeInfoBuilder.toDto(storeInfo);
	}

	public void updateStoreInfo(StoreInfoRequest infoRequest) {

		StoreInfo storeInfo = storeInfoRepository.findByStoreName(infoRequest.getStoreName());

		if (storeInfo != null && storeInfo.getId() != infoRequest.getId()) {
			throw CommonException.CreateException(CommonExceptionMessage.ALREADY_EXISTS, infoRequest.getStoreName());
		}
		
		storeInfo.setStoreName(infoRequest.getStoreName());
		storeInfo.setCorpStoreNo(infoRequest.getCorpStoreNo());
		storeInfo.setSerialNumber(infoRequest.getSerialNumber());
		storeInfo.setAddress(infoRequest.getAddress());
		storeInfo.setBankName(infoRequest.getBankName());
		storeInfo.setAccountNumber(infoRequest.getAccountNumber());
		storeInfo.setMinimumBalance(infoRequest.getMinimumBalance());
		storeInfo.setStoreName(infoRequest.getStoreName());
		storeInfo.setConfigured(infoRequest.isConfigured());

		
		storeInfoRepository.save(storeInfo);

	}

	
	public void deleteByStoreInfo(Long Id) {
		

		storeInfoRepository.deleteById(Id);
	}
	
	public void configureStore(StoreInfoRequest storeInfoRequest) {
		StoreInfo storeInfo = storeInfoRepository.findById(storeInfoRequest.getId()).get();
		storeInfo.setConfigured(true);
		storeInfoRepository.save(storeInfo);

	}

//	public void assignStore(Long storeId, Long userId, Long lId) {
//		
//		StoreInfo storeInfo = storeInfoRepository.findById(storeId).get();
//
//		Optional<UserInfo> optional = userInfoRepository.findById(userId);
//		
//		Optional<Locks> optionalL = locksRepository.findById(lId);
//		
//		if (optional.isPresent()) {
//			UserInfo dbUser = optional.get();
//			if (optionalL.isPresent()) {
//				Locks dbLocks = optionalL.get();
//			if (storeInfo != null) {
//				dbUser.setStoreInfo(storeInfo);
//			} else {
//				throw CommonException.CreateException(CommonExceptionMessage.NOTFOUND, "Store");
//			}
//			
//			locksRepository.save(dbLocks);
//			}
//			
//			else {
//				throw CommonException.CreateException(CommonExceptionMessage.NOTFOUND, "Locks");
//			}
//
//			userInfoRepository.save(dbUser);
//		}
//		
//			else {
//			throw CommonException.CreateException(CommonExceptionMessage.NOTFOUND, "User");
//		}
//
//	}
	
	public void assignStore(Long storeId, Long userId) {
		
		StoreInfo storeInfo = storeInfoRepository.findById(storeId).get();

		Optional<UserInfo> optional = userInfoRepository.findById(userId);
		
		
		if (optional.isPresent()) {
			UserInfo dbUser = optional.get();
		
			if (storeInfo != null) {
				dbUser.setStoreInfo(storeInfo);
			} else {
				throw CommonException.CreateException(CommonExceptionMessage.NOTFOUND, "Store");
			}
			
		

			userInfoRepository.save(dbUser);
		}
		
			else {
			throw CommonException.CreateException(CommonExceptionMessage.NOTFOUND, "User");
		}

	}
	
	


	public List<StoreInfoResponse> findUnassignedStores() {
		List<StoreInfo> storeInfos = storeInfoRepository.findByConfigured(false);

		return storeInfoBuilder.toDtoList(storeInfos);
	}

	public List<StoreInfoResponse> findAssignedStores() {
		List<StoreInfo> storeInfos = storeInfoRepository.findByConfigured(true);

		return storeInfoBuilder.toDtoList(storeInfos);
	}
}
