package com.ingeniawork.ticketflow.domain.evento;

import com.ingeniawork.ticketflow.domain.boleta.Boleta;
import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;

public record DatosRegistroEvento(
        String nombre,
        String ciudad,
        String direccion,
        @Future
        LocalDateTime fechaInicialDelEvento,
        @Future
        LocalDateTime fechaFinalDelEvento
) {
}
