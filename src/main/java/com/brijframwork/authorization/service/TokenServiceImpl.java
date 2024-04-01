package com.brijframwork.authorization.service;

import static com.brijframwork.authorization.constant.Constants.ROLE;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenServiceImpl implements TokenService{
	
	private static final Logger log = LoggerFactory.getLogger(TokenServiceImpl.class);

	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437"; 
	
    private String createToken(Map<String, Object> claims, String userName, String role) { 
    	log.debug("TokenServiceImpl :: createToken() started");
        return Jwts.builder() 
                .setClaims(claims) 
                .setSubject(userName)
                .setHeaderParam(ROLE, role)
                .setIssuedAt(new Date(System.currentTimeMillis())) 
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) 
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact(); 
    } 
  
    private Key getSignKey() { 
        byte[] keyBytes= Decoders.BASE64.decode(SECRET); 
        return Keys.hmacShaKeyFor(keyBytes); 
    } 
  
    private Claims extractAllClaims(String token) { 
        return Jwts 
                .parserBuilder() 
                .setSigningKey(getSignKey()) 
                .build() 
                .parseClaimsJws(token) 
                .getBody(); 
    } 
    
    private JwsHeader<?> extractAllHeaders(String token) { 
        return Jwts 
                .parserBuilder() 
                .setSigningKey(getSignKey()) 
                .build() 
                .parseClaimsJws(token)
                .getHeader();             
    } 
  
    private Boolean isTokenExpired(String token) { 
        return extractExpiration(token).before(new Date()); 
    }
    

    public String extractUsername(String token) { 
        return extractClaim(token, Claims::getSubject); 
    } 
    
    public String extractRole(String token) { 
        return extractAllHeaders(token).get(ROLE).toString();
    } 
  
    public Date extractExpiration(String token) { 
        return extractClaim(token, Claims::getExpiration); 
    } 
  
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { 
        final Claims claims = extractAllClaims(token); 
        return claimsResolver.apply(claims); 
    } 

    public String generateToken(String userName,String role) { 
    	log.debug("TokenServiceImpl :: generateToken() started");
        Map<String, Object> claims = new HashMap<>(); 
        return createToken(claims, userName,role); 
    } 

    public Boolean validateToken(String token, String tusername) { 
    	log.debug("TokenServiceImpl :: validateToken() started");
        final String username = extractUsername(token); 
        return (username.equals(tusername) && !isTokenExpired(token)); 
    }  

}
