package com.ingeniawork.ticketflow.domain.tipoboleta;

public record DatosRegistroTipoDeBoleta(
        String perfil,
        int cantidad,
        Long idEvento
) {
}
