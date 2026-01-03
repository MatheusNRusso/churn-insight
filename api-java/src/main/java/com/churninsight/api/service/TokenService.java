package com.churninsight.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.churninsight.api.model.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.issuer}")
    private String issuer;

    @Value("${api.security.token.expiration-seconds}")
    private long expirationSeconds;


    public String generateToken(Account account) {
        try {
            var algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(account.getEmail())
                    .withExpiresAt(Instant.now().plusSeconds(expirationSeconds))
                    .sign(algorithm);

        } catch (JWTCreationException ex) {
            throw new RuntimeException("Could not generate JWT token.", ex);
        }
    }

    public String getSubject(String token) {
        try {
            var algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException ex) {
            return null;
        }
    }
}
