package com.brijframework.authorization.service;

import static com.brijframework.authorization.constant.Constants.BEARER;
import static com.brijframework.authorization.constant.Constants.ROLE;
import static com.brijframework.authorization.constant.Constants.TOKEN_PREFIX;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.authorization.beans.UserDetailResponse;
import com.brijframework.authorization.exceptions.InvalidTokenException;
import com.brijframework.authorization.mapper.UserDetailMapper;
import com.brijframework.authorization.model.EOUserAccount;
import com.brijframework.authorization.model.EOUserToken;
import com.brijframework.authorization.repository.UserAccountRepository;
import com.brijframework.authorization.repository.UserTokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenServiceImpl implements TokenService {

	private static final Logger log = LoggerFactory.getLogger(TokenServiceImpl.class);

	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	@Autowired
	private UserTokenRepository userTokenRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private UserDetailMapper userDetailMapper;

	private String createToken(Map<String, Object> claims, String userName, String role) {
		log.debug("TokenServiceImpl :: createToken() started");
		return Jwts.builder().setClaims(claims).setSubject(userName).setHeaderParam(ROLE, role)
				.setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(buildExprireationDate())
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	private JwsHeader<?> extractAllHeaders(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getHeader();
	}

	private Boolean isTokenExpired(String token) {
		try {
			Date extractExpiration = extractExpiration(token);
			return new Date().after(extractExpiration);
		} catch (ExpiredJwtException e) {
			return true;
		}
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public String extractRole(String token) {
		final JwsHeader<?> jwsHeader = extractAllHeaders(token);
		if (jwsHeader == null) {
			return null;
		}
		return jwsHeader.get(ROLE).toString();
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		if (claims == null) {
			return null;
		}
		return claimsResolver.apply(claims);
	}

	@Override
	public String generateToken(String userName, String role) {
		log.debug("TokenServiceImpl :: generateToken() started");
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName, role);
	}

	@Override
	public String changeExpiration(String token, Date expiration) {
		log.debug("TokenServiceImpl :: generateToken() started");
		Jws<Claims> parseClaimsJws = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);

		Claims body = parseClaimsJws.getBody();
		JwsHeader<?> header = parseClaimsJws.getHeader();

		return Jwts.builder().setClaims(parseClaimsJws.getBody()).setSubject(body.getSubject())
				.setHeaderParam(ROLE, header.get(ROLE)).setIssuedAt(body.getIssuedAt()).setExpiration(expiration)
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
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
		return !isTokenExpired(eoToken.getTarget());
	}

	@Override
	public Date buildExprireationDate() {
		return new Date(System.currentTimeMillis() + 1000 * 60 * 30);
	}

	@Override
	public String login(String username, String role) {
		String token = generateToken(username, role);
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
		if(isTokenExpired(eoToken.getTarget())) {
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
		if(isTokenExpired(eoToken.getTarget())) {
			throw new InvalidTokenException("Invalid token");
		}
		String username = this.extractUsername(token);
		Optional<EOUserAccount> findUserLogin = userAccountRepository.findByUsername(username);
		EOUserAccount eoUserAccount = findUserLogin.orElseThrow(() -> new RuntimeException("Not found!"));
		// userOnBoardingService.initOnBoarding(eoUserAccount);
		return userDetailMapper.mapToDTO(eoUserAccount);
	}
}
