package com.brijframework.authorization.controller;

import static com.brijframework.authorization.constant.Constants.RESET_LINK_MSG1;
import static com.brijframework.authorization.constant.Constants.RESET_LINK_MSG2;
import static com.brijframework.authorization.constant.Constants.SEND_LINK_MSG1;
import static com.brijframework.authorization.constant.Constants.SEND_LINK_MSG2;
import static com.brijframework.authorization.constant.Constants.SEND_LINK_VALID_TIME;
import static com.brijframework.authorization.constant.Constants.TOKEN;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframework.authorization.adptor.AuthProvider;
import com.brijframework.authorization.adptor.EnvironmentUtil;
import com.brijframework.authorization.beans.Response;
import com.brijframework.authorization.beans.LoginRequest;
import com.brijframework.authorization.beans.PasswordReset;
import com.brijframework.authorization.beans.RegisterRequest;
import com.brijframework.authorization.beans.UIUserAccount;
import com.brijframework.authorization.constant.Authority;
import com.brijframework.authorization.service.MailService;
import com.brijframework.authorization.service.TemplateService;
import com.brijframework.authorization.service.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(AuthController.API_AUTH)
@CrossOrigin("*")
public class AuthController {

	private static final String PASSWORD_RESET_BY_LINK_ENDPOINT = "/password/reset/by/link/";
	private static final String PASSWORD_RESET_BY_OTP_ENDPOINT = "/password/reset/by/otp/";

	static final String API_AUTH = "/api/auth";

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AuthProvider authProvider;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private MailService mailService;

	@Autowired
	private EnvironmentUtil environmentUtil;

	@Autowired
	private TemplateService templateService;

	@PostMapping("/login")
	public Response userLogin(@RequestBody LoginRequest loginRequest) {
		log.debug("User Login start.");
		if(loginRequest.getAuthority()==null) {
			loginRequest.setAuthority(Authority.USER);
		}
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(), loginRequest.getPassword(), getGrantedAuthority(loginRequest.getAuthority().getRoleId())));
		if (authenticate.isAuthenticated()) {
			Response authDTO =authProvider.userLogin(loginRequest);
			return authDTO;
		} else {
			Response authDTO =new Response();
			authDTO.setSuccess("0");
			authDTO.setMessage("Login faild, due to invalid creditional.");
			return authDTO;
		}
	}

	@PostMapping("/register")
	public Response userRegistor(@RequestBody RegisterRequest registerRequest) {
		return authProvider.register(registerRequest);
	}
	
	
	@PostMapping("/logout")
	public String userLogout(@RequestHeader(TOKEN) String token) {
		log.debug("User Login start.");
		return tokenService.logout(token);
	}
	
	@PostMapping("/validate")
	public Boolean userValidate(@RequestHeader(TOKEN) String token) {
		log.debug("User Login start.");
		return tokenService.validateToken(token);
	}

	@GetMapping("/userdetail")
    public ResponseEntity<?> getUserDetailFromToken(@RequestHeader(TOKEN) String token) throws AuthenticationException {
    	return ResponseEntity.ok(tokenService.getUserDetailFromToken(token));
    }
	

	@PostMapping("/password/send/link")
	public Boolean sendLink(@RequestBody PasswordReset passwordReset) {
		log.debug("AuthController::sendOtp() start.");
		Random resetToken = new Random();
		int otp = resetToken.nextInt(9999);
		passwordReset.setOtp(otp);
		UIUserAccount uiUserAccount = authProvider.saveOtp(passwordReset);
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
			PasswordReset passwordReset = objectMapper.readValue(decoder.decode(link), PasswordReset.class);
			UIUserAccount uiUserAccount=authProvider.resetPassword(passwordReset);
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
	public Boolean sendOtp(@RequestBody PasswordReset passwordReset) {
		log.debug("AuthController::sendOtp() start.");
		Random resetToken = new Random();
		int otp = resetToken.nextInt(9999);
		passwordReset.setOtp(otp);
		UIUserAccount userDetails=authProvider.saveOtp(passwordReset);
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("name", "Hey " + userDetails.getAccountName());
		hashMap.put("otp", otp + "");
		hashMap.put("date", Calendar.getInstance().getTime().toString());
		String buildHtml = templateService.buildHtml("mailOtp", hashMap);
		return mailService.sendMail("Password OTP", userDetails.getRegisteredEmail(), userDetails.getRegisteredEmail(), buildHtml);
	}

	@PostMapping(PASSWORD_RESET_BY_OTP_ENDPOINT)
	public Boolean resetPassword(@RequestBody PasswordReset passwordReset) {
		log.debug("AuthController::resetPassword() start.");
		authProvider.resetPassword(passwordReset);
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
