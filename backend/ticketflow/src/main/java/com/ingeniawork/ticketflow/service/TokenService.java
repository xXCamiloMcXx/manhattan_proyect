package com.ingeniawork.ticketflow.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.ingeniawork.ticketflow.domain.usuario.Usuario;
import com.ingeniawork.ticketflow.exceptions.AccesoDenegadoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;



@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    private Long userId;

    public String generarToken(Usuario usuario){
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API ticketflow")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(fechaExpiracion())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            // Invalid Signing configuration / Couldn't convert Claims.
            throw new RuntimeException("Error al general el token JWT", exception);
        }
    }

    private Instant fechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }


    public String getSubject(String tokenJWT){
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    // specify any specific claim validations
                    .withIssuer("API ticketflow")
                    // reusable verifier instance
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new AccesoDenegadoException("Token JWT invalido o expirado");

            // Invalid signature/claims
        }
    }

    public Claim getClaim(String tokenJWT, String seleccion){
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    // specify any specific claim validations
                    .withIssuer("API ticketflow")
                    // reusable verifier instance
                    .build()
                    .verify(tokenJWT)
                    .getClaim(seleccion);
        } catch (JWTVerificationException exception){
            throw new AccesoDenegadoException("Token JWT invalido o expirado");

            // Invalid signature/claims
        }
    }


}