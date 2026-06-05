package com.ingeniawork.ticketflow.domain.tipoboleta;

import java.math.BigDecimal;

public record DatosRegistroTipoDeBoleta(
        String perfil,
        int cantidad,
        BigDecimal costo,
        Long idEvento
) {
}
