package com.safesmart.safesmart.service;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.safesmart.safesmart.common.CommonException;
import com.safesmart.safesmart.common.CommonExceptionMessage;
import com.safesmart.safesmart.dto.KioskRequest;
import com.safesmart.safesmart.dto.KioskResponse;
import com.safesmart.safesmart.dto.UserInfoRequest;
import com.safesmart.safesmart.dto.UserInfoResponse;
import com.safesmart.safesmart.model.Kiosk;
import com.safesmart.safesmart.model.Role;
import com.safesmart.safesmart.model.UserInfo;
import com.safesmart.safesmart.repository.KioskRepository;

@Service
@Transactional
public class KioskService {
	
	@Autowired
	private KioskRepository kioskRepository;
	
	public void add(KioskRequest kioskRequest) {

		Kiosk kiosk = kioskRepository.findByKioskName(kioskRequest.getKioskName());
		if (kiosk != null) {
			throw CommonException.CreateException(CommonExceptionMessage.ALREADY_EXISTS, "KioskName");
		}
	

		kiosk = new Kiosk();
		kiosk.setKioskId(kioskRequest.getKioskId());
		kiosk.setKioskName(kioskRequest.getKioskName());
		kiosk.setBrandName(kioskRequest.getBrandName());
		kiosk.setModelName(kioskRequest.getModelName());
		kiosk.setCpu(kioskRequest.getCpu());
		kiosk.setHdd(kioskRequest.getHdd());
		kiosk.setMemory(kioskRequest.getMemory());
		kiosk.setScreenSize(kioskRequest.getScreenSize());
		kiosk.setActive(kioskRequest.isActive());

		kioskRepository.save(kiosk);
	}
	
	public List<KioskResponse> findAllUser() {
		// TODO Auto-generated method stub
		List<Kiosk> kiosks = (List<Kiosk>) kioskRepository.findAll();

		List<KioskResponse> kioskResponses = new ArrayList<KioskResponse>();

		for (Kiosk kiosk : kiosks) {
			kioskResponses.add(new KioskResponse(kiosk.getId(),kiosk.getKioskId(), kiosk.getKioskName(), kiosk.getBrandName(),
					kiosk.getModelName(), kiosk.getCpu(), kiosk.getHdd(), kiosk.getMemory(), kiosk.getScreenSize(), kiosk.isActive()));
		}
		return kioskResponses;
	}
	
	public void deleteByKiosk(Long Id) {
		kioskRepository.deleteById(Id);
	}
	
	public void updateKiosk(KioskRequest kioskRequest) {


		Kiosk kiosk = kioskRepository.findById(kioskRequest.getId()).get();

		kiosk.setKioskId(kioskRequest.getKioskId());
		kiosk.setKioskName(kioskRequest.getKioskName());
		kiosk.setBrandName(kioskRequest.getBrandName());
		kiosk.setModelName(kioskRequest.getModelName());
		kiosk.setCpu(kioskRequest.getCpu());
		kiosk.setHdd(kioskRequest.getHdd());
		kiosk.setMemory(kioskRequest.getMemory());
		kiosk.setScreenSize(kioskRequest.getScreenSize());
		kiosk.setActive(kioskRequest.isActive());
		
		kioskRepository.save(kiosk);

	}

}
