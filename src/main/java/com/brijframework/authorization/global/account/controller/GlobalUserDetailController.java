package com.brijframework.authorization.global.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.QueryController;
import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.model.UIUserAccount;
import com.brijframework.authorization.account.model.UIUserProfile;
import com.brijframework.authorization.account.model.UserDetailResponse;
import com.brijframework.authorization.account.model.auth.GlobalRegisterRequest;
import com.brijframework.authorization.account.service.UserAccountService;

@RestController
@RequestMapping("/api/global/user/detail")
@CrossOrigin("*")
public class GlobalUserDetailController implements QueryController<UserDetailResponse, EOUserAccount, Long> {
	
	@Autowired
    private UserAccountService userDetailService;
	
	@Override
	public QueryService<UserDetailResponse, EOUserAccount, Long> getService() {
		return userDetailService;
	}
	
	@PostMapping
	public ResponseEntity<?> register(@RequestBody GlobalRegisterRequest registerRequest){
    	return ResponseEntity.ok(userDetailService.register(registerRequest));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUserAccount(@PathVariable Long id, @RequestBody UIUserAccount uiUserAccount){
    	return ResponseEntity.ok(userDetailService.updateUserAccount(uiUserAccount));
	}

	@GetMapping("/exists/{username}")
	public ResponseEntity<?> isAlreadyExists(@PathVariable String username){
    	return ResponseEntity.ok(userDetailService.isAlreadyExists(username));
	}
	
	@PutMapping("/profile")
	public ResponseEntity<?> updateUserProfile(@RequestBody UIUserProfile uiUserProfile){
    	return ResponseEntity.ok(userDetailService.updateUserProfile(uiUserProfile));
	}
	
	@PutMapping("/onboarding/{id}/{onboarding}/{idenNo}")
	public ResponseEntity<?> updateOnboarding(@PathVariable Long id, @PathVariable Boolean onboarding,@PathVariable String idenNo){
    	return ResponseEntity.ok(userDetailService.updateOnboarding(id, onboarding, idenNo));
	}
	
	@GetMapping("/profile/{id}")
	public ResponseEntity<?> getUserProfile(@PathVariable Long id){
    	return ResponseEntity.ok(userDetailService.getUserProfile(id));
	}
	
}
