package com.ingeniawork.ticketflow.domain.boleta;

import java.time.LocalDateTime;

public record DatosDetalleBoleta(
        Long id,
        String usuario,
        String asiento,
        LocalDateTime fechaDeCompra,
        LocalDateTime fechaDeVencimiento,
        EstadoBoleta estadoBoleta
) {
    public DatosDetalleBoleta(Boleta boleta) {
        this(boleta.getId(), boleta.getUsuario().getNombre(), boleta.getAsiento(), boleta.getFechaDeCompra(),boleta.getFechaDeVencimiento(),boleta.getEstadoBoleta());
    }
}
