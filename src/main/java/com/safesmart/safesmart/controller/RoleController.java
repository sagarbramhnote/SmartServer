package com.safesmart.safesmart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.safesmart.safesmart.dto.LocksRequest;
import com.safesmart.safesmart.dto.RoleDto;
import com.safesmart.safesmart.service.RoleService;

@RestController
@RequestMapping(value = "/role")
@CrossOrigin
public class RoleController {

	@Autowired
	private RoleService roleService;
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public void add(@RequestBody RoleDto roleDto) {
		roleService.add(roleDto);
	}

	@RequestMapping(value = "updateroles/{Id}", method = RequestMethod.PUT)
	public void updateRoles(@PathVariable("Id") Long Id, @RequestBody RoleDto roleDto) {
		roleDto.setId(Id);
		roleService.updateRoles(roleDto);
	}
	
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<RoleDto> findAll() {
		System.out.println("Coming into find all roles method ");
		return roleService.findAll();
	}
	
	@RequestMapping(value = "/{Id}", method = RequestMethod.DELETE)
	public void deleteByRole(@PathVariable("Id") Long Id) {
		roleService.deleteByRoles(Id);
	}
	
	
}
