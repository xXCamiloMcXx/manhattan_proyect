package com.ingeniawork.ticketflow.domain.tipoboleta;

import java.math.BigDecimal;

public record DatosDetalleTipoDeBoleta(
        Long id,
        String perfil,
        int cantidad,
        BigDecimal costo,
        Long idEvento
) {
    public DatosDetalleTipoDeBoleta (TipoDeBoleta tipoDeBoleta){
        this(tipoDeBoleta.getId(), tipoDeBoleta.getPerfil(), tipoDeBoleta.getCantidad(),tipoDeBoleta.getCosto(), tipoDeBoleta.getEvento().getId());
    }
}
