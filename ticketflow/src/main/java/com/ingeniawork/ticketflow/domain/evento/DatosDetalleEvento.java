package com.ingeniawork.ticketflow.domain.evento;

import jakarta.validation.constraints.Future;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DatosDetalleEvento(
        Long id,
        String nombre,
        String ciudad,
        String direccion,
        String categoria,
        String frasePromocional,
        BigDecimal precio,
        EstadoEvento estadoEvento,
        @Future
        LocalDateTime fechaInicialDelEvento,
        @Future
        LocalDateTime fechaFinalDelEvento
) {
    public DatosDetalleEvento(Evento evento) {
        this(
                evento.getId(),
                evento.getNombre(),
                evento.getCiudad(),
                evento.getDireccion(),
                evento.getCategoria(),
                evento.getFrasePromocional(),
                evento.getPrecio() ,
                evento.getEstadoEvento(),
                evento.getFechaInicialDelEvento(),
                evento.getFechaFinalDelEvento());
    }
}
