package com.safesmart.safesmart.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.safesmart.safesmart.dto.ChangeRequestDto;
import com.safesmart.safesmart.model.ChangeRequest;
import com.safesmart.safesmart.model.OrderStatus;
import com.safesmart.safesmart.model.UserInfo;
import com.safesmart.safesmart.repository.ChangeRequestRepository;
import com.safesmart.safesmart.repository.UserInfoRepository;
import com.safesmart.safesmart.util.EmailTemplate;

@Service
public class ChangeRequestService {

	@Autowired
	private ChangeRequestRepository changeRequestRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private EmailTemplate emailTemplate;
	
	public void createChangeRequest(ChangeRequestDto changeRequestDto) {
		ChangeRequest changeRequest = new ChangeRequest();
		BeanUtils.copyProperties(changeRequestDto, changeRequest);
		Optional<UserInfo> optional = userInfoRepository.findById(changeRequestDto.getUpdatedByUser());
		if (optional.isPresent()) {
			changeRequest.setCreatedBy(optional.get());
			changeRequest.setCreated(LocalDateTime.now());
		}
		changeRequest.setOrderStatus(changeRequestDto.getOrderStatus());
		changeRequestRepository.save(changeRequest);
		emailTemplate.createChangeRequestMail(changeRequestDto);
		//send mail to truck owner
	}
	
	

	public List<ChangeRequest> findAll() {
		return changeRequestRepository.findAll();
	}
	
	public ChangeRequest findByTypeAndOrderStatus(String type, String orderStatus) {
		return  changeRequestRepository.findByTypeAndOrderStatus(type, orderStatus);
	}

}
