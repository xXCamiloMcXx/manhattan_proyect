package com.ingeniawork.ticketflow.domain.evento;

import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;

public record DatosActualizacionEvento(
        String nombre,
        String ciudad,
        String direccion,
        @Future
        LocalDateTime fechaInicialDelEvento,
        @Future
        LocalDateTime fechaFinalDelEvento
) {
}
