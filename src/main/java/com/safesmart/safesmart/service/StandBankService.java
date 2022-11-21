package com.safesmart.safesmart.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safesmart.safesmart.dto.ValetDenominationsDto;
import com.safesmart.safesmart.model.UserInfo;
import com.safesmart.safesmart.model.ValetDenominations;
import com.safesmart.safesmart.repository.UserInfoRepository;
import com.safesmart.safesmart.repository.ValetDenominationsRepository;

@Service
public class StandBankService {

	@Autowired
	private ValetDenominationsRepository valetDenominationsRepository;
	
	@Autowired
	private UserInfoRepository userInfoRepository;

	public void createDenominations(ValetDenominationsDto valetDenominationsDto) {
		ValetDenominations dbvaletDenominations = valetDenominationsRepository.findByType(valetDenominationsDto.getType());
		ValetDenominations valetDenominations = convertToModel(valetDenominationsDto);
		Optional<UserInfo> optional = userInfoRepository.findById(valetDenominationsDto.getUpdatedByUser());
		
		if (dbvaletDenominations == null) {
			if (optional.isPresent()) {
				valetDenominations.setCreatedBy(optional.get());
				valetDenominations.setCreated(LocalDateTime.now());
			}
			
		} else {
			if (optional.isPresent()) {
				valetDenominations.setModifiedBy(optional.get());
				valetDenominations.setModified(LocalDateTime.now());
			}
			//throw new RuntimeException("Denomination already created.");
		}
		valetDenominationsRepository.save(valetDenominations);
	}

	private ValetDenominations convertToModel(ValetDenominationsDto valetDenominationsDto) {
		ValetDenominations valetDenominations = new ValetDenominations();
		BeanUtils.copyProperties(valetDenominationsDto, valetDenominations);
		return valetDenominations;
	}

	public List<ValetDenominations> findAllDenominations() {
		return (List<ValetDenominations>) valetDenominationsRepository.findAll();
	}

	public ValetDenominations findDenominations(String type) {
		return valetDenominationsRepository.findByType(type);
	}

}
