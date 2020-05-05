package com.bridgelabz.bookstoreapi.utility;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTUtil {
	
//	@Value("${secret}")
//	private String SECRET;
	private static final String SECRET = "2129152050365";

	private final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	public String generateToken(Long id, Token expiration) {
		if (expiration.equals(Token.WITH_EXPIRE_TIME)) {
			return Jwts.builder().setSubject(String.valueOf(id))
					.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
					.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		} else {
			return Jwts.builder().setSubject(String.valueOf(id)).signWith(SignatureAlgorithm.HS512, SECRET).compact();
		}
	}

	public Long decodeToken(String jwt)  {
		System.out.println("Decoder*****");
		Claims claim = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwt).getBody();
		Long id = Long.parseLong(claim.getSubject());
		return id;
	}
	
}
