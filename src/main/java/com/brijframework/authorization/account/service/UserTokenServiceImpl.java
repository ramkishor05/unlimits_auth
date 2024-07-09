package com.brijframework.authorization.account.service;

import static com.brijframework.authorization.constant.Constants.BEARER;
import static com.brijframework.authorization.constant.Constants.TOKEN_PREFIX;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unlimits.rest.context.ApiTokenContext;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.entities.EOUserToken;
import com.brijframework.authorization.account.model.UserDetailResponse;
import com.brijframework.authorization.account.repository.UserAccountRepository;
import com.brijframework.authorization.account.repository.UserTokenRepository;
import com.brijframework.authorization.exceptions.InvalidTokenException;
import com.brijframework.authorization.global.account.mapper.GlobalUserDetailMapper;

@Service
public class UserTokenServiceImpl implements UserTokenService {

	@Autowired
	private UserTokenRepository userTokenRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private GlobalUserDetailMapper userDetailMapper;

	@Override
	public String generateToken(String userName, Long userId, String role, String serviceType) {
		Map<String, Object> claims = new HashMap<>();
		return ApiTokenContext.createToken(claims, userName,userId, role, serviceType);
	}

	@Override
	public String changeExpiration(String token, Date expiration) {
		return ApiTokenContext.updateExpiry(token, expiration);
	}
	
	@Override
	public String extendExpiration(String authToken) {
		String token = authToken.startsWith(BEARER) ? authToken.substring(7) : authToken;
		Optional<EOUserToken> findBySource = userTokenRepository.findBySource(token);
		if (!findBySource.isPresent()) {
			return "";
		}
		EOUserToken eoToken = findBySource.get();
		eoToken.setTarget(ApiTokenContext.extendExpiry(token));
		userTokenRepository.save(eoToken);
		return eoToken.getTarget();
	}

	@Override
	public Boolean validateToken(String authToken) {
		String token = authToken.startsWith(BEARER) ? authToken.substring(7) : authToken;
		Optional<EOUserToken> findBySource = userTokenRepository.findBySource(token);
		if (!findBySource.isPresent()) {
			return false;
		}
		EOUserToken eoToken = findBySource.get();
		return !ApiTokenContext.isTokenExpired(eoToken.getTarget());
	}

	@Override
	public Date buildExprireationDate() {
		return ApiTokenContext.generateExpriyDate();
	}

	@Override
	public String login(String username, Long userId, String role, String serviceType) {
		String token = generateToken(username, userId, role, serviceType);
		EOUserToken eoToken = new EOUserToken(token, token, serviceType,
				userAccountRepository.findByUsername(username).orElse(null));
		userTokenRepository.save(eoToken);
		return TOKEN_PREFIX + token;
	}

	@Override
	public String logout(String authToken) {
		String token = authToken.startsWith(BEARER) ? authToken.substring(7) : authToken;
		Optional<EOUserToken> findBySource = userTokenRepository.findBySource(token);
		if (!findBySource.isPresent()) {
			return "Failed logout";
		}
		EOUserToken eoToken = findBySource.get();
		if(ApiTokenContext.isTokenExpired(eoToken.getTarget())) {
			return "Already logout";
		}
		String target = changeExpiration(token, new Date(System.currentTimeMillis()));
		eoToken.setTarget(target);
		userTokenRepository.save(eoToken);
		return "Sucessfully logout";
	}

	@Override
	public UserDetailResponse getUserDetailFromToken(String authToken) {
		String token = authToken.startsWith(BEARER) ? authToken.substring(7) : authToken;
		Optional<EOUserToken> findBySource = userTokenRepository.findBySource(token);
		if (!findBySource.isPresent()) {
			throw new InvalidTokenException("Invalid token");
		}
		EOUserToken eoToken = findBySource.get();
		if(ApiTokenContext.isTokenExpired(eoToken.getTarget())) {
			throw new InvalidTokenException("Invalid token");
		}
		String username = this.extractUsername(token);
		Optional<EOUserAccount> findUserLogin = userAccountRepository.findByUsername(username);
		EOUserAccount eoUserAccount = findUserLogin.orElseThrow(() -> new RuntimeException("Not found!"));
		// userOnBoardingService.initOnBoarding(eoUserAccount);
		return userDetailMapper.mapToDTO(eoUserAccount);
	}

	@Override
	public String extractUsername(String token) {
		return ApiTokenContext.getUsername(token);
	}

	@Override
	public String extractRole(String token) {
		return ApiTokenContext.getUserRole(token);
	}
}
