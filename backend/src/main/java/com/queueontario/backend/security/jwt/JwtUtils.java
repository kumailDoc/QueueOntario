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
     * Extracts JWT from the Authorization header (Bearer <token>).
     * If no Authorization header exists, it tries to retrieve it from cookies.
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            String token = headerAuth.substring(7); // Remove "Bearer " prefix
            logger.info("JWT extracted from Authorization header: {}", token);
            return token;
        }

        // Optionally fall back to cookie-based extraction (if you still need cookies)
        return getJwtFromCookies(request);
    }

    /**
     * Extract JWT from cookies (optional, fallback method)
     */
    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
            logger.info("JWT extracted from Cookie: {}", cookie.getValue());
            return cookie.getValue();
        } else {
            logger.warn("No JWT found in cookies.");
            return null;
        }
    }

    /**
     * Generate a JWT token and store it in an HTTP-only cookie (optional)
     */
    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername());
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt)
                .path("/api")
                .maxAge(24 * 60 * 60)
                .httpOnly(true)
                .build();
        logger.info("🔥 JWT Cookie generated for user: {}", userPrincipal.getUsername());
        return cookie;
    }

    /**
     * Clear the JWT cookie
     */
    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
        logger.info("🔥 JWT cookie cleared.");
        return cookie;
    }

    /**
     * Extract the username from the JWT token
     */
    public String getUserNameFromJwtToken(String token) {
        try {
            String username = Jwts.parserBuilder().setSigningKey(key()).build()
                    .parseClaimsJws(token).getBody().getSubject();
            logger.info("🔥 Extracted username from JWT: {}", username);
            return username;
        } catch (JwtException e) {
            logger.error("🔥 Failed to extract username from JWT: {}", e.getMessage());
            throw e; // Optional: Re-throw to log or handle issues more clearly
        }
    }

    /**
     * Return the signing key based on the secret
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * Validate the JWT token
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            logger.info("🔥 JWT token is valid");
            return true;
        } catch (MalformedJwtException e) {
            logger.error("🔥 Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("🔥 JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("🔥 JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("🔥 JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Generate a JWT token from a given username
     */
    public String generateTokenFromUsername(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
        logger.info("🔥 JWT generated for username: {}", username);
        return token;
    }
}