package com.brijframework.authorization.account.service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.brijframework.util.text.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.entities.EOUserAccountService;
import com.brijframework.authorization.account.entities.EOUserProfile;
import com.brijframework.authorization.account.entities.EOUserRole;
import com.brijframework.authorization.account.mapper.UserProfileMapper;
import com.brijframework.authorization.account.model.UIUserAccount;
import com.brijframework.authorization.account.model.UIUserProfile;
import com.brijframework.authorization.account.model.UserDetailResponse;
import com.brijframework.authorization.account.model.auth.GlobalAuthDataDTO;
import com.brijframework.authorization.account.model.auth.GlobalLoginRequest;
import com.brijframework.authorization.account.model.auth.GlobalPasswordReset;
import com.brijframework.authorization.account.model.auth.GlobalRegisterRequest;
import com.brijframework.authorization.account.repository.UserAccountRepository;
import com.brijframework.authorization.account.repository.UserAccountServiceRepository;
import com.brijframework.authorization.account.repository.UserProfileRepository;
import com.brijframework.authorization.account.repository.UserRoleRepository;
import com.brijframework.authorization.constant.Authority;
import com.brijframework.authorization.constant.RecordStatus;
import com.brijframework.authorization.constant.ServiceType;
import com.brijframework.authorization.device.account.model.DeviceLoginRequest;
import com.brijframework.authorization.exceptions.UserNotFoundException;
import com.brijframework.authorization.global.account.mapper.GlobalUserDetailMapper;
import com.brijframework.authorization.global.account.service.UserOnBoardingService;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class UserAccountServiceImpl extends CrudServiceImpl<UserDetailResponse, EOUserAccount, Long> implements UserAccountService {
	
	private static final String RECORD_STATE = "recordState";

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
	
	@Autowired
	private UserProfileMapper userProfileMapper;

	@Autowired
	private UserAccountServiceRepository userAccountServiceRepository;

	@Override
	public JpaRepository<EOUserAccount, Long> getRepository() {
		return userAccountRepository;
	}

	@Override
	public GenericMapper<EOUserAccount, UserDetailResponse> getMapper() {
		return userDetailMapper;
	}
	
	{
		CustomPredicate<EOUserAccount> userRoleId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOUserRole> subquery = criteriaQuery.subquery(EOUserRole.class);
			Root<EOUserRole> fromUserRole = subquery.from(EOUserRole.class);
			subquery.select(fromUserRole)
					.where(criteriaBuilder.equal(fromUserRole.get("id"), Long.valueOf(filter.getColumnValue().toString())));
			Path<Object> userRolePath = root.get("userRole");
			In<Object> userRoleIn = criteriaBuilder.in(userRolePath);
			userRoleIn.value(subquery);
			return userRoleIn;
		};
		
		CustomPredicate<EOUserAccount> userRoleName = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOUserRole> subquery = criteriaQuery.subquery(EOUserRole.class);
			Root<EOUserRole> fromUserRole = subquery.from(EOUserRole.class);
			subquery.select(fromUserRole)
					.where(criteriaBuilder.equal(fromUserRole.get("roleName"), filter.getColumnValue().toString()));
			Path<Object> userRolePath = root.get("userRole");
			In<Object> userRoleIn = criteriaBuilder.in(userRolePath);
			userRoleIn.value(subquery);
			return userRoleIn;
		};

		addCustomPredicate("userRoleId", userRoleId);
		addCustomPredicate("userRole.id", userRoleId);
		addCustomPredicate("userRoleName", userRoleName);
		addCustomPredicate("userRole.name", userRoleName);
	}
	
	{
		getCustomSortingMap().put("userRoleId", "userRole.roleName");
	}
	
	@Override
	public UIUserAccount loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<EOUserAccount> findUserLogin = userAccountRepository.findByUsername(username, RecordStatus.ACTIVETED.getStatusIds());
		if (!findUserLogin.isPresent()) {
			throw new UserNotFoundException();
		}
		return getUserDetail(findUserLogin.get());
	}

	@Override
	public  UIUserAccount getUserDetail(EOUserAccount eoUserAccount) {
		UIUserAccount userDetails = userDetailMapper.mapToUI(eoUserAccount);
		userDetails.setAuthorities(getGrantedAuthority(eoUserAccount.getUserRole().getRoleType()));
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
	public GlobalAuthDataDTO register(GlobalRegisterRequest registerRequest) {
		if(isAlreadyExists(registerRequest.getUsername()) || isAlreadyExists(registerRequest.getRegisteredPhone()) || isAlreadyExists(registerRequest.getRegisteredEmail())) {
			return tryLogin(registerRequest);
		}
		EOUserRole eoUserRole = userRoleRepository.findByPosition(registerRequest.getAuthority().getPosition()).orElse(null);
		
		EOUserProfile eoUserProfile=new EOUserProfile();
		eoUserProfile.setFullName(registerRequest.getAccountName());
		eoUserProfile = userProfileRepository.save(eoUserProfile);
		
		EOUserAccount eoUserAccount=new EOUserAccount();
		eoUserAccount.setUsername(registerRequest.getUsername());
		if(StringUtil.isNonEmpty(registerRequest.getPassword())) {
			eoUserAccount.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		}
		eoUserAccount.setType(eoUserRole.getRoleId());
		eoUserAccount.setRegisteredMobile(StringUtil.isEmpty(registerRequest.getRegisteredPhone()) ? null : registerRequest.getRegisteredPhone().trim());
		eoUserAccount.setRegisteredEmail(StringUtil.isEmpty(registerRequest.getRegisteredEmail()) ? null : registerRequest.getRegisteredEmail().trim());
		eoUserAccount.setAccountName(registerRequest.getAccountName());
		eoUserAccount.setUserRole(eoUserRole);
		eoUserAccount.setUserProfile(eoUserProfile);
		eoUserAccount.setOnBoarding(true);	
		eoUserAccount.setRecordState(RecordStatus.ACTIVETED.getStatus());
		eoUserAccount=userAccountRepository.save(eoUserAccount);
		this.initService(eoUserAccount);
		userOnBoardingService.initOnBoarding(eoUserAccount);
		GlobalAuthDataDTO authDataDTO = new GlobalAuthDataDTO();
		authDataDTO.setUser(userDetailMapper.mapToDetailDTO(eoUserAccount));
		authDataDTO.setToken(tokenService.login(registerRequest.getUsername(), eoUserAccount.getId(), registerRequest.getAuthority().toString(), registerRequest.getServiceType().toString()));
		return authDataDTO;
	}
	
	protected void initService(EOUserAccount eoUserAccount){
		Map<String, EOUserAccountService> userAccountServiceMap = userAccountServiceRepository.findByUserAccountId(eoUserAccount.getId()).stream().collect(Collectors.toMap(EOUserAccountService::getType, Function.identity()));
	
		for(ServiceType serviceType: ServiceType.values()) {
			EOUserAccountService eoUserAccountService = userAccountServiceMap.getOrDefault(serviceType.toString(), new EOUserAccountService(serviceType.toString(), eoUserAccount));
			userAccountServiceRepository.save(eoUserAccountService);
		}
	}
	
	@Override
	public GlobalAuthDataDTO tryLogin(GlobalRegisterRequest loginRequest) {
		if(StringUtil.isEmpty(loginRequest.getUsername())) {
			if(StringUtil.isNonEmpty(loginRequest.getRegisteredEmail())) {
				loginRequest.setUsername(loginRequest.getRegisteredEmail());
				return login(loginRequest);
			}
			if(StringUtil.isNonEmpty(loginRequest.getRegisteredPhone())) {
				loginRequest.setUsername(loginRequest.getRegisteredPhone());
				return login(loginRequest);
			}
		}
		return login(loginRequest);
	}
	
	@Override
	public GlobalAuthDataDTO login(GlobalLoginRequest loginRequest) {
		Optional<EOUserAccount> findUserLogin = userAccountRepository.findByUsername(loginRequest.getUsername(), RecordStatus.ACTIVETED.getStatusIds());
		if (!findUserLogin.isPresent()) {
			throw new UserNotFoundException();
		}
		EOUserAccount eoUserAccount = findUserLogin.get();
		GlobalAuthDataDTO authDataDTO = new GlobalAuthDataDTO();
		authDataDTO.setUser(userDetailMapper.mapToDetailDTO(eoUserAccount));
		authDataDTO.setToken(tokenService.login(loginRequest.getUsername(), eoUserAccount.getId(), eoUserAccount.getUserRole().getRoleName(), loginRequest.getServiceType().toString()));
		return authDataDTO;
	}

	@Override
	public Boolean isAlreadyExists(String username) {
		if(StringUtil.isEmpty(username)) {
			return false;
		}
		return userAccountRepository.findByUsername(username, RecordStatus.ALL.getStatusIds()).isPresent();
	}
	
	@Override
	public  Boolean deleteById(Long uuid) {
		Optional<EOUserAccount> userOptional = userAccountRepository.findById(uuid);
		if(userOptional.isPresent()) {
			EOUserAccount eoUserProfile = userOptional.get();
			eoUserProfile.setRecordState(RecordStatus.DACTIVETED.getStatus());
			userAccountRepository.saveAndFlush(eoUserProfile);
			return true;
		}
		return false;
	}

	@Override
	public UIUserProfile  updateUserProfile(UIUserProfile uiUserProfile) {
		EOUserProfile eoUserProfile=userProfileRepository.findById(uiUserProfile.getId()).orElse(new EOUserProfile());
		BeanUtils.copyProperties(uiUserProfile, eoUserProfile,"id");
		eoUserProfile = userProfileRepository.save(eoUserProfile);
		return uiUserProfile;
	}
	
	@Override
	public UIUserProfile updateUserProfile(EOUserAccount eoUserAccount, UIUserProfile uiUserProfile) {
		EOUserProfile eoUserProfile=Optional.ofNullable(eoUserAccount.getUserProfile()).orElse(new EOUserProfile());
		BeanUtils.copyProperties(uiUserProfile, eoUserProfile,"id");
		eoUserProfile = userProfileRepository.save(eoUserProfile);
		eoUserAccount.setUserProfile(eoUserProfile);
		eoUserAccount=userAccountRepository.save(eoUserAccount);
		return userProfileMapper.mapToDTO(eoUserProfile);
	}

	@Override
	public UserDetailResponse updateUserAccount(UIUserAccount uiUserAccount) {
		EOUserAccount eoUserAccount=userAccountRepository.findById(uiUserAccount.getId()).orElse(null);
		//eoUserAccount.setUsername(uiUserAccount.getUsername());
		//eoUserAccount.setPassword(uiUserAccount.getPassword());
		eoUserAccount.setType(uiUserAccount.getType());
		eoUserAccount.setAccountName(uiUserAccount.getAccountName());
		eoUserAccount.setRegisteredMobile(uiUserAccount.getRegisteredPhone());
		eoUserAccount.setRegisteredEmail(uiUserAccount.getRegisteredEmail());
		eoUserAccount=userAccountRepository.save(eoUserAccount);
		return userDetailMapper.mapToDTO(eoUserAccount);
	}
	
	@Override
	public Boolean passwordUpdateByToken(EOUserAccount currentAccount, DeviceLoginRequest deviceLoginRequest) {
		currentAccount.setPassword(passwordEncoder.encode(deviceLoginRequest.getPassword()));
		userAccountRepository.save(currentAccount);
		return true;
	}

	@Override
	public UIUserProfile getUserProfile(Long id) {
		EOUserProfile eoUserProfile=userProfileRepository.findById(id).orElse(null);
		return userProfileMapper.mapToDTO(eoUserProfile);
	}
	
	@Override
	public UIUserProfile getUserProfile(EOUserAccount currentAccount) {
		EOUserProfile eoUserProfile = Optional.ofNullable(currentAccount.getUserProfile()).orElse(new EOUserProfile());
		eoUserProfile = userProfileRepository.save(eoUserProfile);
		eoUserProfile.setUserAccount(currentAccount);
		userAccountRepository.save(currentAccount);
		return userProfileMapper.mapToDTO(eoUserProfile);
	}

	@Override
	public List<UserDetailResponse> getUsers() {
		return postFetch(userAccountRepository.findAll());
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
		Optional<EOUserAccount> findUserAccount = userAccountRepository.findByUsername(username, RecordStatus.ACTIVETED.getStatusIds());
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
		Optional<EOUserAccount> findUserAccount = userAccountRepository.findByUsername(passwordReset.getUsername(), RecordStatus.ACTIVETED.getStatusIds());
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
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters) {
		if (filters != null && !filters.containsKey(RECORD_STATE)) {
			filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
		}
		
		filters.put("userRoleName", Authority.USER.getRoleName());
	}


	@Override
	public Optional<EOUserAccount> find(GlobalLoginRequest authRequest) {
		return userAccountRepository.findByUsername(authRequest.getUsername(), RecordStatus.ACTIVETED.getStatusIds());
	}

}
