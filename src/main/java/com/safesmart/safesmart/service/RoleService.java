package com.safesmart.safesmart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.safesmart.safesmart.builder.RoleBuilder;
import com.safesmart.safesmart.dto.RoleDto;
import com.safesmart.safesmart.model.Role;
import com.safesmart.safesmart.model.UserInfo;
import com.safesmart.safesmart.repository.RoleRepository;

@Service
@Transactional
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RoleBuilder roleBuilder;

	public void add(RoleDto roleDto) {

		Role role = roleBuilder.toModel(roleDto);

		roleRepository.save(role);
	}

	@JsonIgnore
	public List<RoleDto> findAll() {
		List<Role> roles = (List<Role>) roleRepository.findAll();

		return roleBuilder.toDtoList(roles);
	}


}
