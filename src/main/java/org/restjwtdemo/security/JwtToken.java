package org.restjwtdemo.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;

/**
 * Utility class for handling JWT tokens.
 */
public class JwtToken {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    protected static int expirySeconds = 600;
    protected static String secret = "secret";

    public static String createToken(String username) {
        return JWT.create().withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirySeconds * 1000))
                .sign(HMAC512(secret.getBytes()));
    }

    public static String verifyToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC512(secret.getBytes())).build().verify(token).getSubject();
        } catch (JWTDecodeException | NullPointerException e) {
            return null;
        }
    }

    public static boolean hasToken(String header) {
        return header != null && header.startsWith(TOKEN_PREFIX);
    }
}
