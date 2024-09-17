package com.buccodev.auth_api.config;

import com.buccodev.auth_api.repositories.UserRepository;
import com.buccodev.auth_api.services.AuthenticatorService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final AuthenticatorService authenticatorService;
    private final UserRepository userRepository;

    public SecurityFilter(AuthenticatorService authenticatorService, UserRepository userRepository) {
        this.authenticatorService = authenticatorService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var token = extractToken(request);

        if(token != null) {

            var email = authenticatorService.valadationToken(token);

            var user = userRepository.findByEmail(email);

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);

    }

    public String extractToken(HttpServletRequest httpServletRequest){

        var authHeader = httpServletRequest.getHeader("Authorization");

        if(authHeader == null){
            return null;
        }

        if(!authHeader.split(" ")[0].equals("Bearer")){
            return null;
        }

        return authHeader.split(" ")[1];
    }
}
