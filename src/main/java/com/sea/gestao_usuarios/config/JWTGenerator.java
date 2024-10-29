package com.sea.gestao_usuarios.config;

//import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sea.gestao_usuarios.modules.usuarios.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTGenerator{

  SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
  private final String secret = "my-secret-key";

  public String generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Date currentDate = new Date();
            Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(String.valueOf(user.getId()))
                    .withExpiresAt(expireDate)
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

  public String getUsernameFromJWT(String token){
    Claims claims = Jwts.parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(token)
      .getBody();
    
    return claims.getSubject();
  }

  public String validateToken(String token){
    Algorithm algorithm = Algorithm.HMAC256(secret);
    try {
      var subject = JWT.require(algorithm)
        .withIssuer("auth-api")
        .build()
        .verify(token)
        .getSubject();
      return subject;
    } catch (JWTVerificationException exception){
        return "";
    }
  }
}
