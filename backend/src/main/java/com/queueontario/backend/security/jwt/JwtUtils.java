package com.queueontario.backend.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import com.queueontario.backend.security.services.UserDetailsImpl;

import java.security.Key;
import java.util.Date;

/**
 * Utility class for handling JSON Web Tokens (JWTs).
 * This includes generating, validating, extracting information from tokens,
 * and handling JWT cookies.
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${bezkoder.app.jwtSecret}")
    private String jwtSecret;

    @Value("${bezkoder.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${bezkoder.app.jwtCookieName}")
    private String jwtCookie;

    /**
     * Extracts the JWT token from the Authorization header.
     * Falls back to cookies if the Authorization header is not present.
     *
     * @param request the HTTP request containing the Authorization header or cookies
     * @return the JWT token if found, or {@code null} if no token is available
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            String token = headerAuth.substring(7); // Remove "Bearer " prefix
            logger.info("JWT extracted from Authorization header: {}", token);
            return token;
        }

        return getJwtFromCookies(request);
    }

    /**
     * Extracts the JWT token from cookies.
     *
     * @param request the HTTP request containing the cookies
     * @return the JWT token if found in cookies, or {@code null} if not present
     */
    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    /**
     * Generates an HTTP-only cookie containing a JWT token.
     *
     * @param userPrincipal the user details for whom the token is being generated
     * @return a {@link ResponseCookie} containing the JWT token
     */
    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername());
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt)
                .path("/api")
                .maxAge(24 * 60 * 60)
                .httpOnly(true)
                .build();
        logger.info("JWT Cookie generated for user: {}", userPrincipal.getUsername());
        return cookie;
    }

    /**
     * Creates a cookie to clear the JWT token.
     *
     * @return a {@link ResponseCookie} with the token cleared
     */
    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
        logger.info("JWT cookie cleared.");
        return cookie;
    }

    /**
     * Extracts the username from the provided JWT token.
     *
     * @param token the JWT token
     * @return the username contained in the token
     * @throws JwtException if the token is invalid or cannot be parsed
     */
    public String getUserNameFromJwtToken(String token) {
        try {
            String username = Jwts.parserBuilder().setSigningKey(key()).build()
                    .parseClaimsJws(token).getBody().getSubject();
            logger.info("Extracted username from JWT: {}", username);
            return username;
        } catch (JwtException e) {
            logger.error("Failed to extract username from JWT: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Provides the signing key derived from the secret key.
     *
     * @return a {@link Key} object for signing and validating tokens
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * Validates the provided JWT token.
     *
     * @param authToken the JWT token to validate
     * @return {@code true} if the token is valid, {@code false} otherwise
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            logger.info("JWT token is valid");
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    /**
     * Generates a JWT token for the given username.
     *
     * @param username the username for which the token is being generated
     * @return the generated JWT token
     */
    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
}
