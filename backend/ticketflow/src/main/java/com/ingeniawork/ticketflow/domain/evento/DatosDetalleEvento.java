package com.ingeniawork.ticketflow.domain.evento;

import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;

public record DatosDetalleEvento(
        String nombre,
        String ciudad,
        String direccion,
        EstadoEvento estadoEvento,
        @Future
        LocalDateTime fechaInicialDelEvento,
        @Future
        LocalDateTime fechaFinalDelEvento
) {
    public DatosDetalleEvento(Evento evento) {
        this(evento.getNombre(),evento.getCiudad(),evento.getDireccion(),evento.getEstadoEvento(), evento.getFechaInicialDelEvento(), evento.getFechaFinalDelEvento());
    }
}
