package com.brijframework.authorization.view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.authorization.view.entities.pages.EOViewPageGroup;
import com.brijframework.authorization.view.model.pages.UIViewPageGroup;
import com.brijframework.authorization.view.service.ViewPageGroupService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/global/page/group")
@CrossOrigin("*")
@Hidden
public class ViewPageGroupController implements CrudController<UIViewPageGroup, EOViewPageGroup, Long>{
	
	@Autowired
    private ViewPageGroupService pageGroupService;
	
	@GetMapping("/role/{roleId}")
	public ResponseEntity<List<UIViewPageGroup>> getPageGroupListByRole(@PathVariable Long roleId){
    	return ResponseEntity.ok(pageGroupService.getPageGroupListByRole(roleId));
	}

	@Override
	public CrudService<UIViewPageGroup, EOViewPageGroup, Long> getService() {
		return pageGroupService;
	}
}
