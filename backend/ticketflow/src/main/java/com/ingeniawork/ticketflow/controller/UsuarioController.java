package com.ingeniawork.ticketflow.controller;

import com.ingeniawork.ticketflow.domain.usuario.DatosDetalleUsuario;
import com.ingeniawork.ticketflow.domain.usuario.DatosRegistroUsuario;
import com.ingeniawork.ticketflow.domain.usuario.Usuario;
import com.ingeniawork.ticketflow.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @PostMapping
    public ResponseEntity registrarUsuario (@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario){
        var hashContrasena = passwordEncoder.encode(datosRegistroUsuario.password());
        var usuario = new Usuario(datosRegistroUsuario,hashContrasena);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar (@PathVariable Long id){
        var usuario = usuarioRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetalleUsuario(usuario));
    }


}
