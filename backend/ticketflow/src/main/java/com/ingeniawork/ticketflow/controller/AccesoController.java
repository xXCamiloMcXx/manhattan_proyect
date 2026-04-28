package com.ingeniawork.ticketflow.controller;

import com.ingeniawork.ticketflow.domain.usuario.DatosAccesoUsuario;
import com.ingeniawork.ticketflow.domain.usuario.Usuario;
import com.ingeniawork.ticketflow.infra.security.DatosTokenJWT;
import com.ingeniawork.ticketflow.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AccesoController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping
    public ResponseEntity iniciarSesion(@RequestBody @Valid DatosAccesoUsuario datos){
        var authenticationToken = new UsernamePasswordAuthenticationToken(datos.email(), datos.contrasena());
        var autenticacion = manager.authenticate(authenticationToken);
        var tokemJWT = tokenService.generarToken((Usuario) autenticacion.getPrincipal());
        return ResponseEntity.ok(new DatosTokenJWT(tokemJWT));
        //return ResponseEntity.ok().build();
    }
}
