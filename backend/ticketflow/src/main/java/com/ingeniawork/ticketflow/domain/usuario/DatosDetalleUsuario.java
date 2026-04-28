package com.ingeniawork.ticketflow.domain.usuario;

import java.time.LocalDate;

public record DatosDetalleUsuario(
        //Informacion Personal
        String nombre,
        String apellido,
        String telefono,
        String cedula,
        LocalDate fechaDeNacimiento,
        String genero,
        String paisDeResidencia,
        //Informacion de cuenta y Acceso
        String email,
        Perfil perfil
) {

    public DatosDetalleUsuario (Usuario usuario) {
        this(usuario.getNombre(),usuario.getApellido(),usuario.getTelefono(),usuario.getCedula(),usuario.getFechaDeNacimiento()
        ,usuario.getGenero(),usuario.getPaisDeResidencia(),usuario.getEmail(),usuario.getPerfil());
    }
}
