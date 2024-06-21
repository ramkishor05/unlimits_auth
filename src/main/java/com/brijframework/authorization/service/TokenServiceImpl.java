package com.brijframework.authorization.service;

import static com.brijframework.authorization.constant.Constants.BEARER;
import static com.brijframework.authorization.constant.Constants.TOKEN_PREFIX;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unlimits.rest.token.TokenUtil;

import com.brijframework.authorization.exceptions.InvalidTokenException;
import com.brijframework.authorization.global.beans.UserDetailResponse;
import com.brijframework.authorization.mapper.UserDetailMapper;
import com.brijframework.authorization.model.EOUserAccount;
import com.brijframework.authorization.model.EOUserToken;
import com.brijframework.authorization.repository.UserAccountRepository;
import com.brijframework.authorization.repository.UserTokenRepository;

@Service
public class TokenServiceImpl implements TokenService {

	private static final Logger log = LoggerFactory.getLogger(TokenServiceImpl.class);

	@Autowired
	private UserTokenRepository userTokenRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private UserDetailMapper userDetailMapper;

	@Override
	public String generateToken(String userName, Long userId, String role) {
		log.debug("TokenServiceImpl :: generateToken() started");
		Map<String, Object> claims = new HashMap<>();
		return TokenUtil.createToken(claims, userName,userId, role);
	}

	@Override
	public String changeExpiration(String token, Date expiration) {
		return TokenUtil.changeExpiration(token, expiration);
	}

	@Override
	public Boolean validateToken(String authToken) {
		String token = authToken.startsWith(BEARER) ? authToken.substring(7) : authToken;
		log.debug("TokenServiceImpl :: validateToken() started");
		Optional<EOUserToken> findBySource = userTokenRepository.findBySource(token);
		if (!findBySource.isPresent()) {
			return false;
		}
		EOUserToken eoToken = findBySource.get();
		return !TokenUtil.isTokenExpired(eoToken.getTarget());
	}

	@Override
	public Date buildExprireationDate() {
		return new Date(System.currentTimeMillis() + 1000 * 60 * 30);
	}

	@Override
	public String login(String username, Long userId, String role) {
		String token = generateToken(username, userId, role);
		EOUserToken eoToken = new EOUserToken(token, token, "NORMAL",
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
		if(TokenUtil.isTokenExpired(eoToken.getTarget())) {
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
		if(TokenUtil.isTokenExpired(eoToken.getTarget())) {
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
		return TokenUtil.getUsername(token);
	}

	@Override
	public String extractRole(String token) {
		return TokenUtil.getUserRole(token);
	}
}
