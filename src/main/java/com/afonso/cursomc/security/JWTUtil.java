package com.afonso.cursomc.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Afonso
 */
@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String pUserName) {
        return Jwts.builder()
                .setSubject(pUserName)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    public boolean tokenValido(String pToken) {
        Claims claims = getClaims(pToken);
        
        if (claims != null) {
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
         
            if (username != null && expirationDate != null && now.before(expirationDate)) {
                return true;
            }
        }
        return false;
    }

    public String getUsername(String pToken) {
        Claims claims = getClaims(pToken);
       
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    private Claims getClaims(String pToken) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(pToken).getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
