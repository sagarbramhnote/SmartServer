package com.safesmart.safesmart.configuration;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.safesmart.safesmart.model.Role;
import com.safesmart.safesmart.model.SequenceInfo;
import com.safesmart.safesmart.model.StoreInfo;
import com.safesmart.safesmart.model.UserInfo;
import com.safesmart.safesmart.repository.RoleRepository;
import com.safesmart.safesmart.repository.SequenceInfoRepository;
import com.safesmart.safesmart.repository.StoreInfoRepository;
import com.safesmart.safesmart.repository.UserInfoRepository;
import com.safesmart.safesmart.util.Base64BasicEncryption;

@Component
public class DatabaseRunner implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private SequenceInfoRepository sequenceInfoRepository;

	@Autowired
	private StoreInfoRepository storeInfoRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private Base64BasicEncryption passwordEncrypt;

	@Override
	public void run(String... args) throws Exception {

		List<Role> roles = (List<Role>) roleRepository.findAll();
		if (roles == null || roles.isEmpty()) {

			List<String> all = new ArrayList<String>();
			all.add("All");

			Role adminRole = new Role();
			adminRole.setName("ADMIN");
			adminRole.setDescription("Administrator");
			adminRole.setFeatures(all);
			adminRole.setWebModule(all);

			List<String> insertBills = new ArrayList<String>();
			insertBills.add("InsertBills");
			Role employeRole = new Role();
			employeRole.setName("EMPLOYEE");
			employeRole.setDescription("Employeee");
			employeRole.setFeatures(insertBills);

			Role manager = new Role();
			manager.setName("MANAGER");
			manager.setDescription("manager");
			manager.setFeatures(all);
			manager.setWebModule(all);

			Role shiftmanager = new Role();
			shiftmanager.setName("SHIFTMANAGER");
			shiftmanager.setDescription("Shift Manager");

			List<String> shiftFeatures = new ArrayList<String>();
			shiftFeatures.add("InsertBills");
			shiftFeatures.add("Admin");
			shiftFeatures.add("Doors");
			shiftFeatures.add("ChangeRequestDoors");
			shiftFeatures.add("StandBank");
			shiftmanager.setFeatures(shiftFeatures);

			Role truck = new Role();
			truck.setName("TRUCK");
			truck.setDescription("Truck");
			List<String> truckFeatures = new ArrayList<String>();
			truckFeatures.add("OTPScreen");
			truckFeatures.add("Valut");
			truck.setFeatures(truckFeatures);
			
			Role owner = new Role();
			owner.setName("OWNER");
			owner.setDescription("owner");
			owner.setFeatures(all);
			owner.setWebModule(all);
			
			Role store_admin = new Role();
			store_admin.setName("STORE_ADMIN");
			store_admin.setDescription("store_admin");
			store_admin.setFeatures(all);
			store_admin.setWebModule(all);
			
			Role super_admin = new Role();
			super_admin.setName("SUPER_ADMIN");
			super_admin.setDescription("super_admin");
			super_admin.setFeatures(all);
			super_admin.setWebModule(all);
			
			Role director_of_operation = new Role();
			director_of_operation.setName("DIRECTOR_OF_OPERATION");
			director_of_operation.setDescription("director_of_operation");
			director_of_operation.setFeatures(all);
			director_of_operation.setWebModule(all);

			roles = new ArrayList<Role>();
			roles.add(adminRole);
			roles.add(employeRole);
			roles.add(manager);
			roles.add(shiftmanager);
			roles.add(truck);
			roles.add(owner);
			roles.add(store_admin);
			roles.add(super_admin);
			roles.add(director_of_operation);

			roleRepository.saveAll(roles);

			SequenceInfo sequenceInfo = new SequenceInfo();

			sequenceInfo.setName("TRANSACTIONNO");
			sequenceInfo.setValue(1);
			sequenceInfoRepository.save(sequenceInfo);

			StoreInfo storeInfo = new StoreInfo();
			storeInfo.setSerialNumber("UT0");
			storeInfo.setCorpStoreNo("ABC");
			storeInfo.setStoreName("XYZ");
			storeInfo.setStartTime(LocalTime.now());
			storeInfo.setEndTime(LocalTime.NOON);
			storeInfo.setStatus(true);

			storeInfoRepository.save(storeInfo);

			UserInfo userInfo = new UserInfo();
			userInfo.setUsername("Admin");
			userInfo.setPassword(passwordEncrypt.encodePassword("1234"));
			userInfo.setRole(roleRepository.findByName("ADMIN"));
			userInfo.setStoreInfo(storeInfoRepository.findByStoreName("XYZ"));
			userInfoRepository.save(userInfo);

			

		}
	}

}
