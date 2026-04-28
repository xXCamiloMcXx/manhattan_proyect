package com.ingeniawork.ticketflow.domain.usuario;

import java.time.LocalDate;

public record DatosRegistroUsuario(
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
        String nombreUsuario,
        String password,
        Perfil perfil
) {
}
