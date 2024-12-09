package com.queueontario.backend.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements {@link AuthenticationEntryPoint} and is used to handle
 * unauthorized access attempts by providing a standardized JSON response.
 *
 * When an unauthorized request is made, this entry point will intercept the request
 * and return a response with status code 401 (Unauthorized) along with additional
 * details in the response body.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    /**
     * Handles an unauthorized access attempt by sending a 401 error response
     * with a JSON body that contains details about the error.
     *
     * @param request       the HTTP request that resulted in an {@link AuthenticationException}
     * @param response      the HTTP response to be sent back to the client
     * @param authException the exception that triggered this entry point
     * @throws IOException      if an input or output error is detected
     * @throws ServletException if the processing fails for some reason
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());

        // Set the response content type and status code
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Prepare the JSON response body with error details
        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        // Write the JSON response to the output stream
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
