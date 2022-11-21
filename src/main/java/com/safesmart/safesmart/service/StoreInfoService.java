package com.safesmart.safesmart.service;

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
import com.safesmart.safesmart.model.StoreInfo;
import com.safesmart.safesmart.model.UserInfo;
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
		storeInfo = storeInfoBuilder.toModel(infoRequest);
		storeInfoRepository.save(storeInfo);

	}
	
	public void configureStore(StoreInfoRequest storeInfoRequest) {
		StoreInfo storeInfo = storeInfoRepository.findById(storeInfoRequest.getId()).get();
		storeInfo.setConfigured(true);
		storeInfoRepository.save(storeInfo);

	}

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
		} else {
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
