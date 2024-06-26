package com.brijframework.authorization.account.service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.beans.Response;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.entities.EOUserProfile;
import com.brijframework.authorization.account.entities.EOUserRole;
import com.brijframework.authorization.account.model.UIUserAccount;
import com.brijframework.authorization.account.model.UIUserProfile;
import com.brijframework.authorization.account.model.UserDetailResponse;
import com.brijframework.authorization.account.model.auth.GlobalAuthDataDTO;
import com.brijframework.authorization.account.model.auth.GlobalLoginRequest;
import com.brijframework.authorization.account.model.auth.GlobalPasswordReset;
import com.brijframework.authorization.account.model.auth.GlobalRegisterRequest;
import com.brijframework.authorization.account.repository.UserAccountRepository;
import com.brijframework.authorization.account.repository.UserProfileRepository;
import com.brijframework.authorization.account.repository.UserRoleRepository;
import com.brijframework.authorization.constant.Authority;
import com.brijframework.authorization.exceptions.UserAlreadyExistsException;
import com.brijframework.authorization.exceptions.UserNotFoundException;
import com.brijframework.authorization.global.account.mapper.GlobalUserDetailMapper;
import com.brijframework.authorization.global.account.service.UserOnBoardingService;

@Service
public class UserAccountServiceImpl extends QueryServiceImpl<UserDetailResponse, EOUserAccount, Long> implements UserAccountService {
	
	/**
	 * 
	 */
	private static final String _1 = "1";

	/**
	 * 
	 */
	private static final String SUCCUSSFULLY_PROCESSED = "Succussfully processed.";

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	private GlobalUserDetailMapper userDetailMapper;

	@Autowired
	private UserOnBoardingService userOnBoardingService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserTokenService tokenService;
	

	@Override
	public JpaRepository<EOUserAccount, Long> getRepository() {
		return userAccountRepository;
	}

	@Override
	public GenericMapper<EOUserAccount, UserDetailResponse> getMapper() {
		return userDetailMapper;
	}
	
	@Override
	public UIUserAccount loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<EOUserAccount> findUserLogin = userAccountRepository.findByUsername(username);
		if (!findUserLogin.isPresent()) {
			throw new UserNotFoundException();
		}
		EOUserAccount eoUserAccount = findUserLogin.get();
		UIUserAccount userDetails = userDetailMapper.mapToUI(eoUserAccount);
		userDetails.setAuthorities(getGrantedAuthority(eoUserAccount.getUserRole().getRoleId()));
		return userDetails;
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
	
	@Override
	public Response register(GlobalRegisterRequest registerRequest) {
		if(isAlreadyExists(registerRequest.getUsername())) {
			throw new UserAlreadyExistsException();
		}
		if(registerRequest.getAuthority()==null) {
			registerRequest.setAuthority(Authority.USER);
		}
		EOUserRole eoUserRole = userRoleRepository.findByPosition(registerRequest.getAuthority().getPosition()).orElse(null);
		
		EOUserProfile eoUserProfile=new EOUserProfile();
		eoUserProfile.setFullName(eoUserRole.getRoleName());
		eoUserProfile = userProfileRepository.save(eoUserProfile);
		
		EOUserAccount eoUserAccount=new EOUserAccount();
		eoUserAccount.setUsername(registerRequest.getUsername());
		eoUserAccount.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		eoUserAccount.setType(eoUserRole.getRoleId());
		eoUserAccount.setRegisteredMobile(registerRequest.getRegisteredPhone());
		eoUserAccount.setRegisteredEmail(registerRequest.getRegisteredEmail());
		eoUserAccount.setAccountName(registerRequest.getAccountName());
		eoUserAccount.setUserRole(eoUserRole);
		eoUserAccount.setUserProfile(eoUserProfile);
		eoUserAccount.setOnBoarding(true);		
		eoUserAccount=userAccountRepository.save(eoUserAccount);
		userOnBoardingService.initOnBoarding(eoUserAccount);
		Response auth=new Response();
		auth.setSuccess(_1);
		auth.setMessage(SUCCUSSFULLY_PROCESSED);
		GlobalAuthDataDTO authDataDTO = new GlobalAuthDataDTO();
		authDataDTO.setUser(userDetailMapper.mapToDetailDTO(eoUserAccount));
		authDataDTO.setToken(tokenService.login(registerRequest.getUsername(), eoUserAccount.getId(), registerRequest.getAuthority().toString()));
		auth.setData(authDataDTO);
		return auth;
	}
	
	@Override
	public Response login(GlobalLoginRequest loginRequest) {
		Optional<EOUserAccount> findUserLogin = userAccountRepository.findByUsername(loginRequest.getUsername());
		if (!findUserLogin.isPresent()) {
			throw new UserNotFoundException();
		}
		EOUserAccount eoUserAccount = findUserLogin.get();
		Response auth=new Response();
		auth.setSuccess(_1);
		auth.setMessage(SUCCUSSFULLY_PROCESSED);
		
		GlobalAuthDataDTO authDataDTO = new GlobalAuthDataDTO();
		authDataDTO.setUser(userDetailMapper.mapToDetailDTO(eoUserAccount));
		authDataDTO.setToken(tokenService.login(loginRequest.getUsername(), eoUserAccount.getId(), loginRequest.getAuthority().toString()));
		auth.setData(authDataDTO);
		return auth;
	}

	@Override
	public boolean isAlreadyExists(String username) {
		return userAccountRepository.findByUsername(username).isPresent();
	}

	@Override
	public UIUserProfile updateUserProfile(UIUserProfile uiUserProfile) {
		EOUserProfile eoUserProfile=userProfileRepository.findById(uiUserProfile.getId()).orElse(null);
		eoUserProfile.setTitle(uiUserProfile.getTitle());
		eoUserProfile.setFullName(uiUserProfile.getFullName());
		eoUserProfile.setPreferredName(uiUserProfile.getPreferredName());
		eoUserProfile.setPictureURL(uiUserProfile.getPictureURL());
		eoUserProfile = userProfileRepository.save(eoUserProfile);
		return uiUserProfile;
	}

	@Override
	public UserDetailResponse updateUserAccount(UIUserAccount uiUserAccount) {
		EOUserAccount eoUserAccount=userAccountRepository.findById(uiUserAccount.getId()).orElse(null);
		eoUserAccount.setUsername(uiUserAccount.getUsername());
		eoUserAccount.setPassword(uiUserAccount.getPassword());
		eoUserAccount.setType(uiUserAccount.getType());
		eoUserAccount.setAccountName(uiUserAccount.getAccountName());
		eoUserAccount.setRegisteredMobile(uiUserAccount.getRegisteredPhone());
		eoUserAccount.setRegisteredEmail(uiUserAccount.getRegisteredEmail());
		eoUserAccount=userAccountRepository.save(eoUserAccount);
		return userDetailMapper.mapToDTO(eoUserAccount);
	}

	@Override
	public UIUserProfile getUserProfile(Long id) {
		EOUserProfile eoUserProfile=userProfileRepository.findById(id).orElse(null);
		UIUserProfile uiUserProfile=new UIUserProfile();
		uiUserProfile.setTitle(eoUserProfile.getTitle());
		uiUserProfile.setFullName(eoUserProfile.getFullName());
		uiUserProfile.setPreferredName(eoUserProfile.getPreferredName());
		uiUserProfile.setPictureURL(eoUserProfile.getPictureURL());
		return uiUserProfile;
	}

	@Override
	public List<UserDetailResponse> getUsers() {
		return userDetailMapper.mapToDTO(userAccountRepository.findAll());
	}

	@Override
	public boolean updateOnboarding(Long id, boolean onboarding , String idenNo) {
		Optional<EOUserAccount> findUserAccount = userAccountRepository.findById(id);
		if(findUserAccount.isPresent()) {
			EOUserAccount eoUserAccount = findUserAccount.get();
			return userOnBoardingService.updateOnBoardingStatus(onboarding, idenNo, eoUserAccount);
		}
		return false;
	}

	@Override
	public UIUserAccount resetPassword(GlobalPasswordReset passwordReset) {
		String username = passwordReset.getUsername();
		Optional<EOUserAccount> findUserAccount = userAccountRepository.findByUsername(username);
		if(!findUserAccount.isPresent()) {
			throw new UserNotFoundException(UserNotFoundException.USER_NOT_EXISTS_IN_SYSTEM +" for username :" + username);
		}
		EOUserAccount userAccount = findUserAccount.get();
		if(!passwordReset.getOtp().toString().equals(userAccount.getResetPasswordToken())) {
			System.out.println("Invalid otp");
			throw new UserNotFoundException("Invalid otp " + username);
		}
		Timestamp reset_password_sent_at = userAccount.getResetPasswordSentAt();
		
		if(reset_password_sent_at==null || reset_password_sent_at.after(new Date())) {
			throw new UserNotFoundException("Otp expired " + username);
		}
		userAccount.setPassword(passwordEncoder.encode(passwordReset.getPassword()));
		userAccount.setResetPasswordToken(null);
		userAccount.setResetPasswordSentAt(null);
		userAccountRepository.save(userAccount);
		UIUserAccount userDetails = userDetailMapper.mapToUI(userAccount);
		return userDetails;
	}

	@Override
	public UIUserAccount saveOtp(GlobalPasswordReset passwordReset) {
		Optional<EOUserAccount> findUserAccount = userAccountRepository.findByUsername(passwordReset.getUsername());
		if(!findUserAccount.isPresent()) {
			throw new UserNotFoundException(UserNotFoundException.USER_NOT_EXISTS_IN_SYSTEM +" for username :" + passwordReset.getUsername());
		}
		EOUserAccount userAccount = findUserAccount.get();
		userAccount.setResetPasswordToken(passwordReset.getOtp()+"");
		userAccount.setResetPasswordSentAt(new Timestamp(System.currentTimeMillis()));
		userAccountRepository.save(userAccount);
		UIUserAccount userDetails = userDetailMapper.mapToUI(userAccount);
		return userDetails;
	}

	@Override
	public Optional<EOUserAccount> find(GlobalLoginRequest authRequest) {
		return userAccountRepository.findByUsername(authRequest.getUsername());
	}


}
