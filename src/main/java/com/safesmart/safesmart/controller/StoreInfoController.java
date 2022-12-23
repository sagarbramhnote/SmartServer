package com.safesmart.safesmart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.safesmart.safesmart.dto.LocksResponse;
import com.safesmart.safesmart.dto.StoreInfoRequest;
import com.safesmart.safesmart.dto.StoreInfoResponse;
import com.safesmart.safesmart.dto.UserInfoRequest;
import com.safesmart.safesmart.model.UserInfo;
import com.safesmart.safesmart.service.StoreInfoService;
import com.safesmart.safesmart.service.UserService;

@RestController
@RequestMapping(value = "/storeinfo")
@CrossOrigin
public class StoreInfoController {

	@Autowired
	private StoreInfoService storeInfoService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public StoreInfoResponse findStoreInfo() {
		return storeInfoService.getStoreInfoService();
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public void addStore(@RequestBody StoreInfoRequest storeInfoRequest) {

		storeInfoService.addStore(storeInfoRequest);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<StoreInfoResponse> findAll() {
		return storeInfoService.findAll();
	}

	@RequestMapping(value = "/{storeName}", method = RequestMethod.GET)
	public StoreInfoResponse findByStoreName(@PathVariable("storeName") String storeName) {
		return storeInfoService.findByStoreName(storeName);
	}

	@RequestMapping(value = "/{storeId}", method = RequestMethod.PUT)
	public void updateStoreInfo(@PathVariable("storeId") Long storeId, @RequestBody StoreInfoRequest infoRequest) {
		infoRequest.setId(storeId);
		storeInfoService.updateStoreInfo(infoRequest);

	}
	
	@RequestMapping(value = "/{Id}", method = RequestMethod.DELETE)
	public void deleteByStoreInfo(@PathVariable("Id") Long Id) {
		storeInfoService.deleteByStoreInfo(Id);
	}

	@RequestMapping(value = "/configure", method = RequestMethod.POST)
	public void configureStore(@RequestBody StoreInfoRequest storeInfoRequest) {

		storeInfoService.configureStore(storeInfoRequest);
	}
	
	@RequestMapping(value = "/assign/store/{storeId}/user/{userId}", method = RequestMethod.POST)
	public void assignStore(@PathVariable(value = "storeId") Long storeId,
			@PathVariable(value = "userId") Long userId) {

		storeInfoService.assignStore(storeId, userId);
	}
	
	@RequestMapping(value = "/assign/store/{storeId}/kiosk/{kId}/billValidator/{bId}/printer/{pId}/locks/{lId}", method = RequestMethod.POST)
	public void assignStoreKBPL(@PathVariable(value = "storeId") Long storeId,
			@PathVariable(value = "kId") Long kId ,
			@PathVariable(value = "bId") Long bId ,
			@PathVariable(value = "pId") Long pId ,
	         @PathVariable(value = "lId") Long lId) {

		storeInfoService.assignStoreKBPL(storeId, kId, bId, pId, lId);
	}

	@RequestMapping(value = "/all/unassigned", method = RequestMethod.GET)
	public List<StoreInfoResponse> findUnassignedStores() {
		return storeInfoService.findUnassignedStores();
	}
	
	@RequestMapping(value = "/all/assigned", method = RequestMethod.GET)
	public List<StoreInfoResponse> findAssignedStores() {
		return storeInfoService.findAssignedStores();
	}
	
	//Find Store to User
	@RequestMapping(value = "/all/assigneduser/{id}", method = RequestMethod.GET)
	public List<UserInfo> findByStoreInfoId(@PathVariable  Long id) {
		return  userService.findByStoreInfo_Id(id);
	}
	
	
	
}
