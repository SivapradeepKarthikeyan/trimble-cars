package com.trimble.trimblecars.middlewares;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


//Since it is a filter it will execute first before controllers
@Component
public class TokenValidator extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(TokenValidator.class);

    private final String KEYCLOAK_INTROSPECTION_URL = "https://mock-keycloak.com/auth/realms/demo/protocol/openid-connect/token/introspect";
    private final String KEYCLOAK_CLIENT_ID = "dummy-client-id";
    private final String KEYCLOAK_CLIENT_SECRET = "dummy-client-secret";


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        // Determine the required role based on the API path
        String requestURI = request.getRequestURI();
        String requiredRole = getRequiredRole(requestURI);

        if (token == null || requiredRole == null || !isValidTokenWithKeycloak(token, requiredRole)) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid or missing token or insufficient permissions");
            logger.info("Token validation :: failed");
            return;
        }

        logger.info("Token validation :: success");
        filterChain.doFilter(request, response);
    }

    private String getRequiredRole(String requestURI) {
        if (requestURI.contains("/admin")) return "admin";
        if (requestURI.contains("/owner")) return "owner";
        if (requestURI.contains("/user")) return "user";
        return null; // If API does not require authentication
    }


    //   ✅Make an actual API call to keycloak (IAM service) to check the token authentication.
    private boolean isValidTokenWithKeycloak(String token, String requiredRole) {
        try {

//            RestTemplate restTemplate = new RestTemplate();
//
//            // Prepare request body
//            String requestBody = "token=" + token +
//                    "&client_id=" + KEYCLOAK_CLIENT_ID +
//                    "&client_secret=" + KEYCLOAK_CLIENT_SECRET;
//
//            // Make a POST request to Keycloak's introspection endpoint
//            String response = restTemplate.postForObject(KEYCLOAK_INTROSPECTION_URL, requestBody, String.class);
//
//            if (response != null) {
//                JSONObject jsonResponse = new JSONObject(response);
//                boolean isActive = jsonResponse.getBoolean("active");
//
//                if (!isActive) return false;
//
//                // Extract roles and check if the required role is present
//                List<String> roles = Arrays.asList(jsonResponse.getJSONObject("realm_access").getJSONArray("roles").toList().toArray(new String[0]));
//
//                return roles.contains(requiredRole);
//            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
