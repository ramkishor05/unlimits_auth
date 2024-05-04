package com.brijframework.authorization.service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.brijframework.authorization.beans.AuthDTO;
import com.brijframework.authorization.beans.AuthDataDTO;
import com.brijframework.authorization.beans.LoginRequest;
import com.brijframework.authorization.beans.PasswordReset;
import com.brijframework.authorization.beans.RegisterRequest;
import com.brijframework.authorization.beans.UIUserAccount;
import com.brijframework.authorization.beans.UIUserProfile;
import com.brijframework.authorization.beans.UserDetailResponse;
import com.brijframework.authorization.constant.Authority;
import com.brijframework.authorization.exceptions.UserAlreadyExistsException;
import com.brijframework.authorization.exceptions.UserNotFoundException;
import com.brijframework.authorization.mapper.UserDetailMapper;
import com.brijframework.authorization.model.EOUserAccount;
import com.brijframework.authorization.model.EOUserProfile;
import com.brijframework.authorization.model.EOUserRole;
import com.brijframework.authorization.repository.UserAccountRepository;
import com.brijframework.authorization.repository.UserProfileRepository;
import com.brijframework.authorization.repository.UserRoleRepository;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	private UserDetailMapper userDetailMapper;

	@Autowired
	private UserOnBoardingService userOnBoardingService;

	@Autowired
	private UserAccountRepository userLoginRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private TokenService tokenService;
	
	@Override
	public UIUserAccount loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<EOUserAccount> findUserLogin = userLoginRepository.findByUsername(username);
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
	public AuthDTO register(RegisterRequest registerRequest) {
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
		AuthDTO auth=new AuthDTO();
		auth.setSuccess("1");
		auth.setMessage("Registration succuss.");
		auth.setData(new AuthDataDTO());
		auth.getData().setUser(userDetailMapper.mapToDetailDTO(eoUserAccount));
		auth.getData().setToken(tokenService.login(registerRequest.getUsername(), registerRequest.getAuthority().toString()));
		return auth;
	}
	
	@Override
	public AuthDTO login(LoginRequest loginRequest) {
		Optional<EOUserAccount> findUserLogin = userLoginRepository.findByUsername(loginRequest.getUsername());
		if (!findUserLogin.isPresent()) {
			throw new UserNotFoundException();
		}
		EOUserAccount eoUserAccount = findUserLogin.get();
		AuthDTO auth=new AuthDTO();
		auth.setSuccess("1");
		auth.setMessage("Registration succuss.");
		auth.setData(new AuthDataDTO());
		auth.getData().setUser(userDetailMapper.mapToDetailDTO(eoUserAccount));
		auth.getData().setToken(tokenService.login(loginRequest.getUsername(), loginRequest.getAuthority().toString()));
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
		userOnBoardingService.initOnBoarding(eoUserAccount);
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
	public UIUserAccount resetPassword(PasswordReset passwordReset) {
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
	public UIUserAccount saveOtp(PasswordReset passwordReset) {
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

}
