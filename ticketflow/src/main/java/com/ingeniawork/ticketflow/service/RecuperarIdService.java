package com.ingeniawork.ticketflow.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecuperarIdService {

    @Autowired
    TokenService tokenService;

    public Long obtenerId(HttpServletRequest request){
        var tokenJWT = recuperarToken(request);
        return tokenService.getClaim(tokenJWT,"id").asLong();
    }


    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null){
            return authorizationHeader.replace("Bearer " , "");
        }
        return null;
    }

}
