package com.safesmart.safesmart.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.safesmart.safesmart.common.CommonException;
import com.safesmart.safesmart.common.CommonExceptionMessage;
import com.safesmart.safesmart.dto.RoleDto;
import com.safesmart.safesmart.dto.RolesDto;
import com.safesmart.safesmart.dto.StoreInfoResponse;
import com.safesmart.safesmart.dto.UserInfoRequest;
import com.safesmart.safesmart.dto.UserInfoResponse;
import com.safesmart.safesmart.model.Role;
import com.safesmart.safesmart.model.UserInfo;
import com.safesmart.safesmart.repository.RoleRepository;
import com.safesmart.safesmart.repository.UserInfoRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private RoleRepository roleRepository;

	public void add(UserInfoRequest userInfoRequest) {

		Role role = roleRepository.findByName(userInfoRequest.getRole());

		UserInfo userInfo = userInfoRepository.findByUsername(userInfoRequest.getUsername());
		if (userInfo != null) {
			throw CommonException.CreateException(CommonExceptionMessage.ALREADY_EXISTS, "Username");
		}
		UserInfo infoPassword = userInfoRepository.findByPassword(userInfoRequest.getPassword());
		if (infoPassword != null) {
			throw CommonException.CreateException(CommonExceptionMessage.ALREADY_EXISTS, "Password");
		}
		//user mobile
		UserInfo usermobile = userInfoRepository.findByMobile(userInfoRequest.getMobile());
		if (usermobile != null) {
			throw CommonException.CreateException(CommonExceptionMessage.ALREADY_EXISTS, "mobile");
		}
 
		//user email
		UserInfo infoMail = userInfoRepository.findByEmail(userInfoRequest.getEmail());
		if (infoMail != null) {
			throw CommonException.CreateException(CommonExceptionMessage.ALREADY_EXISTS, "email");
		}
		
		
		userInfo = new UserInfo();
		userInfo.setId(userInfoRequest.getId());
		userInfo.setRole(role);
		userInfo.setUsername(userInfoRequest.getUsername());
		userInfo.setPassword(userInfoRequest.getPassword());
		userInfo.setCreate_time(LocalDate.now());
		userInfo.setActive(true);
		userInfo.setFirstName(userInfoRequest.getFirstName());
		userInfo.setLastName(userInfoRequest.getLastName());
		userInfo.setEmail(userInfoRequest.getEmail());
		userInfo.setMobile(userInfoRequest.getMobile());
		userInfo.setPassLength(userInfoRequest.getPassLength());
		if(userInfoRequest.getLoggedUserId()!= null) {
		Optional<UserInfo> optionalAdminUser = userInfoRepository.findById(userInfoRequest.getLoggedUserId());
		if (optionalAdminUser.isPresent()) {
			UserInfo dbUser = optionalAdminUser.get();
			if (dbUser != null) {
				if (dbUser.getStoreInfo() != null) {
					userInfo.setStoreInfo(dbUser.getStoreInfo());
				}
			}
		}
		}
		
		userInfoRepository.save(userInfo);
	}

	public List<UserInfoResponse> findAllUser() {
		// TODO Auto-generated method stub
		List<UserInfo> users = (List<UserInfo>) userInfoRepository.findAll();

		List<UserInfoResponse> infoResponses = new ArrayList<UserInfoResponse>();

		for (UserInfo userInfo : users) {
			infoResponses.add(new UserInfoResponse(userInfo.getId(), userInfo.getUsername(), userInfo.getPassword(),
					userInfo.getRole().getName(), userInfo.isActive()));
		}
		return infoResponses;
	}

	public void deleteByUser(Long userId) {
		userInfoRepository.deleteById(userId);
	}

	public UserInfoResponse doLogin(UserInfoRequest infoRequest) {

		UserInfo userInfo = userInfoRepository.findByUsernameAndPassword(infoRequest.getUsername(),infoRequest.getPassword());
		if (userInfo == null) {
			throw CommonException.CreateException(CommonExceptionMessage.INCORRECT_UserNameAndPassword);
		}
//		if (!userInfo.checkfeature(infoRequest.getFeature())) {
//			throw CommonException.CreateException(CommonExceptionMessage.PERMISSION_NOTEXISTS);
//		}
		return new UserInfoResponse(userInfo.getId(), userInfo.getUsername(), userInfo.getPassword(),
				userInfo.getRole().getName(), userInfo.isActive());
	}
	public UserInfoResponse updateUserForm(Long id) {
		UserInfo userInfo = userInfoRepository.findById(id).get();
		System.out.println(userInfo);
		System.out.println(userInfo.getEmail()+ " Here " + userInfo.getFirstName());
		
		UserInfoResponse info = new UserInfoResponse(userInfo.getId(), userInfo.getUsername(), userInfo.getPassword(),
				userInfo.getRole().getName(), userInfo.isActive(),userInfo.getFirstName(),userInfo.getLastName(),userInfo.getEmail(),userInfo.getMobile());
		System.out.println(info.getFirstName()+ " " + info.getLastName());
		return info;
		
	}
	public void updateUser(UserInfoRequest userInfoRequest) {

		Role role = roleRepository.findByName(userInfoRequest.getRole());

		UserInfo userInfo = userInfoRepository.findByUsername(userInfoRequest.getUsername());
		if (userInfo != null && !userInfo.getId().equals(userInfoRequest.getId())) {
			throw CommonException.CreateException(CommonExceptionMessage.ALREADY_EXISTS, "Username");
		}
		UserInfo infoPassword = userInfoRepository.findByPassword(userInfoRequest.getPassword());
		if (infoPassword != null && !infoPassword.getId().equals(userInfoRequest.getId())) {
			throw CommonException.CreateException(CommonExceptionMessage.ALREADY_EXISTS, "Password");
		}

		UserInfo info = userInfoRepository.findById(userInfoRequest.getId()).get();

		info.setPassword(userInfoRequest.getPassword());
		info.setUsername(userInfoRequest.getUsername());
		info.setRole(role);
		info.setActive(userInfoRequest.isActive());
		info.setFirstName(userInfoRequest.getFirstName());
		info.setLastName(userInfoRequest.getLastName());
		info.setEmail(userInfoRequest.getEmail());
		info.setMobile(userInfoRequest.getMobile());
		userInfoRepository.save(info);

	}

	public List<UserInfoResponse> getUserPaginations(int page, int size) {
		Page<UserInfo> userPage = userInfoRepository.findAll(PageRequest.of(page, size));
		List<UserInfo> users = userPage.getContent();
		List<UserInfoResponse> infoResponses = new ArrayList<UserInfoResponse>();
		for (UserInfo userInfo : users) {
			infoResponses.add(new UserInfoResponse(userInfo.getId(), userInfo.getUsername(), userInfo.getPassword(),
					userInfo.getRole().getName(), userInfo.isActive()));
		}
		return infoResponses;
	}

//	public List<UserInfoResponse> findUsersByRole(String role) {
//		List<UserInfo> users = userInfoRepository.findByRole_Name(role);
//		List<UserInfoResponse> infoResponses = new ArrayList<UserInfoResponse>();
//		for (UserInfo userInfo : users) {
//			infoResponses.add(new UserInfoResponse(userInfo.getId(), userInfo.getUsername(), userInfo.getPassword(),
//					userInfo.getRole().getName(), userInfo.isActive()));
//		}
//		return infoResponses;
//	}
	
	public List<UserInfoResponse> findUsersByRole(String role) {
		List<UserInfo> users = userInfoRepository.findByRole_Name(role);
		List<UserInfoResponse> infoResponses = new ArrayList<UserInfoResponse>();
		for (UserInfo userInfo : users) {
			if (userInfo.getStoreInfo() !=null) {
			infoResponses.add(new UserInfoResponse(userInfo.getId(), userInfo.getUsername(), userInfo.getPassword(),
					userInfo.getRole().getName(), userInfo.isActive()));
		}
		}
		return infoResponses;
	}
	
//	List<Long> userIds = StoreInfoResponse.getUserIds();
//	UserInfo user;
//	for(Long userId : userIds) {
//		user = userInfoRepository.findById(userId).get();

	public List<UserInfoResponse> findUserbyRoles(RolesDto rolesDto) {
		List<UserInfo> users = userInfoRepository.findByRole_NameIn(rolesDto.getRoles());
		List<UserInfoResponse> infoResponses = new ArrayList<UserInfoResponse>();
		for (UserInfo userInfo : users) {
			infoResponses.add(new UserInfoResponse(userInfo.getId(), userInfo.getUsername(), userInfo.getPassword(),
					userInfo.getRole().getName(), userInfo.isActive()));
		}
		return infoResponses;
	}

	public List<RoleDto> findAllRoles() {
		// TODO Auto-generated method stub
		List<RoleDto> response = new ArrayList<RoleDto>();
		List<Role> roles = (List<Role>) roleRepository.findAll();
		if (roles != null) {
			for (Role role : roles) {
				RoleDto roleDto = new RoleDto();
				roleDto.setDescription(role.getDescription());
				roleDto.setName(role.getName());
				roleDto.setId(role.getId());
				response.add(roleDto);
			}
		}
		return response;
	}

	public void promoteUser(Long userId, RoleDto roleDto) {
		UserInfo userInfo = userInfoRepository.findById(userId).get();

		Role role = roleRepository.findById(roleDto.getId()).get();
		userInfo.setRole(role);
		userInfoRepository.save(userInfo);

	}

	public List<UserInfoResponse> findUnassignedUserbyRoles(RolesDto rolesDto) {
		List<UserInfo> users = userInfoRepository.findByRole_NameIn(rolesDto.getRoles());
		List<UserInfoResponse> infoResponses = new ArrayList<UserInfoResponse>();
		for (UserInfo userInfo : users) {
			if (userInfo != null && userInfo.getStoreInfo() == null) {
				infoResponses.add(new UserInfoResponse(userInfo.getId(), userInfo.getUsername(), userInfo.getPassword(),
						userInfo.getRole().getName(), userInfo.isActive()));
			}
		}
		return infoResponses;
	}

	public List<UserInfoResponse> findunassignedUsersByRole(String role) {
		List<UserInfo> users = userInfoRepository.findByRole_Name(role);
		List<UserInfoResponse> infoResponses = new ArrayList<UserInfoResponse>();
		for (UserInfo userInfo : users) {
			if (userInfo != null && userInfo.getStoreInfo() == null) {
				infoResponses.add(new UserInfoResponse(userInfo.getId(), userInfo.getUsername(), userInfo.getPassword(),
						userInfo.getRole().getName(), userInfo.isActive()));
			}
		}
		return infoResponses;
	}
	
	public List<UserInfo> findByStoreInfo_Id(Long id){
		return userInfoRepository.findByStoreInfo_Id(id);
	}

}