package com.brijframework.authorization.device.account.controller;

import static com.brijframework.authorization.constant.ClientConstants.FAILED;
import static com.brijframework.authorization.constant.ClientConstants.SUCCESS;
import static com.brijframework.authorization.constant.ClientConstants.SUCCESSFULLY_PROCCEED;
import static com.brijframework.authorization.constant.Constants.RESET_LINK_MSG1;
import static com.brijframework.authorization.constant.Constants.RESET_LINK_MSG2;
import static com.brijframework.authorization.constant.Constants.SEND_LINK_MSG1;
import static com.brijframework.authorization.constant.Constants.SEND_LINK_MSG2;
import static com.brijframework.authorization.constant.Constants.SEND_LINK_VALID_TIME;

import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import org.brijframework.util.text.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.authorization.account.model.UIUserAccount;
import com.brijframework.authorization.account.model.auth.GlobalAuthDataDTO;
import com.brijframework.authorization.account.model.auth.GlobalLoginRequest;
import com.brijframework.authorization.account.model.auth.GlobalPasswordReset;
import com.brijframework.authorization.account.model.auth.GlobalRegisterRequest;
import com.brijframework.authorization.account.service.UserTokenService;
import com.brijframework.authorization.adptor.EnvironmentUtil;
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
	
	private String fromEmail="noreply@clavis.digital";
	
	private String from_subject="Unlimits";
	
	@PostMapping("/login")
	public Response<Object> userLogin(@RequestBody DeviceLoginRequest deviceLoginRequest) {
		log.debug("User Login start.");
		Response<Object> response=new Response<Object>();
		try {
			GlobalLoginRequest loginRequest=new GlobalLoginRequest();
			BeanUtils.copyProperties(deviceLoginRequest, loginRequest);
			Authentication authenticate = 
					ServiceType.NORMAL.equals(loginRequest.getServiceType())?
					authenticationManager.authenticate(new BasicAuthentication(
							loginRequest.getUsername(), loginRequest.getPassword())):
					authenticationManager.authenticate(new SocialAuthentication(
					loginRequest.getUsername()));
			if (authenticate.isAuthenticated()) {
				GlobalAuthDataDTO authDTO =passwordAuthenticationProvider.userLogin(loginRequest);
				response.setSuccess(SUCCESS);
				response.setData(authDTO);
				response.setMessage(SUCCESSFULLY_PROCCEED);
				return response;
			} else {
				response.setSuccess(FAILED);
				response.setMessage("Login faild, due to invalid creditional.");
				return response;
			}
		}catch (Exception e) {
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}

	@PostMapping("/register")
	public Response<Object> userRegistor(@RequestBody DeviceRegisterRequest deviceRegisterRequest) {
		Response<Object> response=new Response<Object>();
		try {
			GlobalRegisterRequest registerRequest=new GlobalRegisterRequest();
			BeanUtils.copyProperties(deviceRegisterRequest, registerRequest);
			response.setSuccess(SUCCESS);
			response.setData(passwordAuthenticationProvider.register(registerRequest));
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	
	@PostMapping("/logout")
	public Response<Object> userLogout() {
		log.debug("User Login start.");
		Response<Object> response=new Response<Object>();
		try {
			TokenAuthentication tokenAuthentication = (TokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
			if(tokenAuthentication==null) {
				response.setData( false);
				response.setSuccess(FAILED);
			} else {
				response.setSuccess(SUCCESS);
				response.setData(tokenService.logout(tokenAuthentication.getToken()));
				response.setSuccess(SUCCESS);
			}
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@PostMapping("/validate")
	public Response<Object> userValidate() {
		log.debug("User Login start.");
		Response<Object> response=new Response<Object>();
		try {
			TokenAuthentication tokenAuthentication = (TokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
			if(tokenAuthentication==null) {
				response.setData( false);
				response.setSuccess(FAILED);
			} else {
				response.setData(tokenService.validateToken(tokenAuthentication.getToken()));
				response.setSuccess(SUCCESS);
			}
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@PutMapping("/password/update")
	public Response<Object> passwordUpdate(@RequestBody DeviceLoginRequest deviceLoginRequest) {
		log.debug("User Login start.");
		Response<Object> response=new Response<Object>();
		try {
			TokenAuthentication tokenAuthentication = (TokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
			if(tokenAuthentication==null) {
				response.setData( false);
				response.setSuccess(FAILED);
			} else
			if( !tokenAuthentication.isAuthenticated()){
				response.setData(false);
				response.setSuccess(FAILED);
			} else {
				response.setData(passwordAuthenticationProvider.passwordUpdateByToken(deviceLoginRequest));
				response.setSuccess(SUCCESS);
			}
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}

	@GetMapping
    public ResponseEntity<?> getUserDetail(@RequestHeader(required =false)  MultiValueMap<String,String> headers) throws AuthenticationException {
		return ResponseEntity.ok(passwordAuthenticationProvider.getUserDetail());
    }


	@PostMapping("/password/send/link")
	public Boolean sendLink(@RequestBody GlobalPasswordReset devicePasswordReset) {
		log.debug("AuthController::sendOtp() start.");
		Random resetPassword = new Random();
		int password = resetPassword.nextInt(99999);
		GlobalPasswordReset passwordReset=new GlobalPasswordReset();
		passwordReset.setPassword(password+"");
		devicePasswordReset.setPassword(password+"");
		BeanUtils.copyProperties(devicePasswordReset, passwordReset);
		Random resetToken = new Random();
		int otp = resetToken.nextInt(9999);
		passwordReset.setOtp(otp);
		UIUserAccount uiUserAccount = passwordAuthenticationProvider.saveOtp(passwordReset);
		HashMap<String, Object> hashMap = new HashMap<>();
		if (uiUserAccount.getAccountName() == null) {
			hashMap.put("name", (StringUtil.isNonEmpty(uiUserAccount.getRegisteredEmail())? uiUserAccount.getRegisteredEmail(): uiUserAccount.getUsername()));
		} else {
			hashMap.put("name", uiUserAccount.getAccountName());
		}
		hashMap.put("date", Calendar.getInstance().getTime().toString());
		hashMap.put("msg1", SEND_LINK_MSG1);
		hashMap.put("validTime", SEND_LINK_VALID_TIME);
		hashMap.put("msg2","Your temporary password is <b>"+password+"</b><br><br> Note: "+  SEND_LINK_MSG2);
		hashMap.put("password", password);
		Encoder encoder = Base64.getEncoder();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String link = encoder.encodeToString(objectMapper.writeValueAsString(passwordReset).getBytes());
			hashMap.put("link", environmentUtil.getServerUrlPrefi() + API_AUTH+PASSWORD_RESET_BY_LINK_ENDPOINT + link);
			resetPasswordLink(link);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String buildHtml = templateService.buildHtml("mailOtp", hashMap);
		return mailService.sendMail(from_subject, fromEmail,
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
		return mailService.sendMail(from_subject, fromEmail, userDetails.getRegisteredEmail(), buildHtml);
	}

	@PostMapping(PASSWORD_RESET_BY_OTP_ENDPOINT)
	public Boolean resetPassword(@RequestBody GlobalPasswordReset passwordReset) {
		log.debug("AuthController::resetPassword() start.");
		passwordAuthenticationProvider.resetPassword(passwordReset);
		return true;
	}

}
