package com.brijframework.authorization.device.account.controller;

import static com.brijframework.authorization.constant.Constants.RESET_LINK_MSG1;
import static com.brijframework.authorization.constant.Constants.RESET_LINK_MSG2;
import static com.brijframework.authorization.constant.Constants.SEND_LINK_MSG1;
import static com.brijframework.authorization.constant.Constants.SEND_LINK_MSG2;
import static com.brijframework.authorization.constant.Constants.SEND_LINK_VALID_TIME;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.authorization.account.model.UIUserAccount;
import com.brijframework.authorization.account.model.auth.GlobalLoginRequest;
import com.brijframework.authorization.account.model.auth.GlobalPasswordReset;
import com.brijframework.authorization.account.model.auth.GlobalRegisterRequest;
import com.brijframework.authorization.account.service.UserTokenService;
import com.brijframework.authorization.adptor.EnvironmentUtil;
import com.brijframework.authorization.constant.Authority;
import com.brijframework.authorization.constant.ServiceType;
import com.brijframework.authorization.device.account.model.DeviceLoginRequest;
import com.brijframework.authorization.device.account.model.DeviceRegisterRequest;
import com.brijframework.authorization.provider.BasicAuthentication;
import com.brijframework.authorization.provider.BasicAuthenticationProvider;
import com.brijframework.authorization.provider.SocialAuthentication;
import com.brijframework.authorization.provider.TokenAuthentication;
import com.brijframework.authorization.service.MailService;
import com.brijframework.authorization.service.TemplateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(DeviceAuthenticationController.API_AUTH)
@CrossOrigin("*")
public class DeviceAuthenticationController {

	private static final String PASSWORD_RESET_BY_LINK_ENDPOINT = "/password/reset/by/link/";
	private static final String PASSWORD_RESET_BY_OTP_ENDPOINT = "/password/reset/by/otp/";

	static final String API_AUTH = "/api/device/authentication";

	private static final Logger log = LoggerFactory.getLogger(DeviceAuthenticationController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private BasicAuthenticationProvider passwordAuthenticationProvider;
	
	@Autowired
	private UserTokenService tokenService;

	@Autowired
	private MailService mailService;

	@Autowired
	private EnvironmentUtil environmentUtil;

	@Autowired
	private TemplateService templateService;
	
	@PostMapping("/login")
	public Response userLogin(@RequestBody DeviceLoginRequest deviceLoginRequest) {
		log.debug("User Login start.");
		GlobalLoginRequest loginRequest=new GlobalLoginRequest();
		BeanUtils.copyProperties(deviceLoginRequest, loginRequest);
		Authentication authenticate = 
				ServiceType.NORMAL.equals(loginRequest.getServiceType())?
				authenticationManager.authenticate(new BasicAuthentication(
						loginRequest.getUsername(), loginRequest.getPassword(), getGrantedAuthority(loginRequest.getAuthority().getRoleId()))):
				authenticationManager.authenticate(new SocialAuthentication(
				loginRequest.getUsername(), null, getGrantedAuthority(loginRequest.getAuthority().getRoleId())));
		if (authenticate.isAuthenticated()) {
			Response authDTO =passwordAuthenticationProvider.userLogin(loginRequest);
			return authDTO;
		} else {
			Response authDTO =new Response();
			authDTO.setSuccess("0");
			authDTO.setMessage("Login faild, due to invalid creditional.");
			return authDTO;
		}
	}

	@PostMapping("/register")
	public Response userRegistor(@RequestBody DeviceRegisterRequest deviceRegisterRequest) {
		GlobalRegisterRequest registerRequest=new GlobalRegisterRequest();
		BeanUtils.copyProperties(deviceRegisterRequest, registerRequest);
		return passwordAuthenticationProvider.register(registerRequest);
	}
	
	
	@PostMapping("/logout")
	public String userLogout() {
		log.debug("User Login start.");
		TokenAuthentication tokenAuthentication = (TokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
		return tokenService.logout(tokenAuthentication.getToken());
	}
	
	@PostMapping("/validate")
	public Boolean userValidate() {
		log.debug("User Login start.");
		TokenAuthentication tokenAuthentication = (TokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
		return tokenService.validateToken(tokenAuthentication.getToken());
	}

	@GetMapping
    public ResponseEntity<?> getUserDetail(@RequestHeader(required =false)  MultiValueMap<String,String> headers) throws AuthenticationException {
		return ResponseEntity.ok(passwordAuthenticationProvider.getUserDetail());
    }


	@PostMapping("/password/send/link")
	public Boolean sendLink(@RequestBody GlobalPasswordReset devicePasswordReset) {
		log.debug("AuthController::sendOtp() start.");
		
		GlobalPasswordReset passwordReset=new GlobalPasswordReset();
		BeanUtils.copyProperties(devicePasswordReset, passwordReset);
		passwordReset.setAuthority(Authority.USER);
		
		Random resetToken = new Random();
		int otp = resetToken.nextInt(9999);
		passwordReset.setOtp(otp);
		UIUserAccount uiUserAccount = passwordAuthenticationProvider.saveOtp(passwordReset);
		HashMap<String, Object> hashMap = new HashMap<>();
		if (uiUserAccount.getAccountName() == null) {
			hashMap.put("name", "Hey, " + uiUserAccount.getRegisteredEmail());
		} else {
			hashMap.put("name", "Hey " + uiUserAccount.getAccountName());
		}
		hashMap.put("date", Calendar.getInstance().getTime().toString());
		hashMap.put("msg1", SEND_LINK_MSG1);
		hashMap.put("validTime", SEND_LINK_VALID_TIME);
		hashMap.put("msg2", SEND_LINK_MSG2);
		Encoder encoder = Base64.getEncoder();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String link = encoder.encodeToString(objectMapper.writeValueAsString(passwordReset).getBytes());
			hashMap.put("link", environmentUtil.getServerUrlPrefi() + API_AUTH+PASSWORD_RESET_BY_LINK_ENDPOINT + link);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String buildHtml = templateService.buildHtml("mailOtp", hashMap);
		return mailService.sendMail("Password OTP", uiUserAccount.getRegisteredEmail(),
				uiUserAccount.getRegisteredEmail(), buildHtml);
	}

	@GetMapping(value = PASSWORD_RESET_BY_LINK_ENDPOINT+"{link}", produces = { MediaType.TEXT_HTML_VALUE })
	public String resetPasswordLink(@PathVariable("link") String link) {
		Decoder decoder = Base64.getDecoder();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			GlobalPasswordReset passwordReset = objectMapper.readValue(decoder.decode(link), GlobalPasswordReset.class);
			UIUserAccount uiUserAccount=passwordAuthenticationProvider.resetPassword(passwordReset);
			HashMap<String, Object> hashMap = new HashMap<>();
			if (uiUserAccount.getAccountName() == null) {
				hashMap.put("name", "Congrats, " + uiUserAccount.getRegisteredEmail());
			} else {
				hashMap.put("name", "Congrats, " + uiUserAccount.getAccountName());
			}
			hashMap.put("date", Calendar.getInstance().getTime().toString());
			hashMap.put("msg1", RESET_LINK_MSG1);
			hashMap.put("msg2", RESET_LINK_MSG2);
			return templateService.buildHtml("mailOtp", hashMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@PostMapping("/password/send/otp")
	public Boolean sendOtp(@RequestBody GlobalPasswordReset passwordReset) {
		log.debug("AuthController::sendOtp() start.");
		Random resetToken = new Random();
		int otp = resetToken.nextInt(9999);
		passwordReset.setOtp(otp);
		UIUserAccount userDetails=passwordAuthenticationProvider.saveOtp(passwordReset);
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("name", "Hey " + userDetails.getAccountName());
		hashMap.put("otp", otp + "");
		hashMap.put("date", Calendar.getInstance().getTime().toString());
		String buildHtml = templateService.buildHtml("mailOtp", hashMap);
		return mailService.sendMail("Password OTP", userDetails.getRegisteredEmail(), userDetails.getRegisteredEmail(), buildHtml);
	}

	@PostMapping(PASSWORD_RESET_BY_OTP_ENDPOINT)
	public Boolean resetPassword(@RequestBody GlobalPasswordReset passwordReset) {
		log.debug("AuthController::resetPassword() start.");
		passwordAuthenticationProvider.resetPassword(passwordReset);
		return true;
	}

	private List<GrantedAuthority> getGrantedAuthority(String authority) {
		return Arrays.asList(new GrantedAuthority() {

			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return authority;
			}
		});
	}

}
