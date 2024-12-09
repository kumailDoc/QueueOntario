package com.queueontario.backend.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import com.queueontario.backend.security.services.UserDetailsServiceImpl;

import java.io.IOException;

/**
 * This filter intercepts every HTTP request and extracts the JWT token from the
 * Authorization header. It validates the token and sets the authentication
 * in the Spring Security context if the token is valid.
 */
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    /**
     * Processes incoming HTTP requests to validate JWT tokens and set the
     * authentication context for the current user if the token is valid.
     *
     * @param request     the incoming HTTP request
     * @param response    the HTTP response
     * @param filterChain the chain of filters to execute
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs during request processing
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Extract and validate the JWT token
            String jwt = parseJwt(request);
            logger.info("Token from request: {}", jwt); // Log the token

            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // Extract the username and load user details
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                logger.info("Username extracted from token: {}", username); // Log the username

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Create authentication object and set it in the SecurityContext
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                logger.info("Authentication set for user: {}", username); // Log successful auth
            } else {
                logger.warn("Token is invalid or null");
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the "Authorization" header.
     * The expected format is "Bearer <TOKEN>".
     *
     * @param request the HTTP request containing the "Authorization" header
     * @return the JWT token, or {@code null} if no valid token is found
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        logger.info("Authorization header: {}", headerAuth); // Log the Authorization header

        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            String token = headerAuth.substring(7); // Remove 'Bearer ' prefix
            logger.info("Extracted token: {}", token); // Log the token
            return token;
        }
        logger.warn("No token found in Authorization header");
        return null;
    }
}
