package com.buccodev.auth_api.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.buccodev.auth_api.dto.AuthDTO;
import com.buccodev.auth_api.model.User;
import com.buccodev.auth_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class AuthenticatorService implements UserDetailsService {

    @Value("${api.secret}")
    private String secret_key;

    private final UserRepository userRepository;

    public AuthenticatorService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public String getToken(AuthDTO authDTO){

        var user = userRepository.findByEmail(authDTO.email());

        return generateToken(user);
    }


    public String generateToken(User user){

        try{

            var algorithm = Algorithm.HMAC256(secret_key);

            return JWT.create().
                    withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00")))
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new RuntimeException("error generate token!"+e.getMessage());
        }

    }

    public String valadationToken(String token){

        try{
            var algorithm = Algorithm.HMAC256(secret_key);

            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e){
            return "";
        }
    }
}
