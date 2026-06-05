package com.ingeniawork.ticketflow.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenUUIDService {
    public String GenerarToken(){
        return UUID.randomUUID().toString();
    }
}
