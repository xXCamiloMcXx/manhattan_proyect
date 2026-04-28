package com.ingeniawork.ticketflow.domain.tipoboleta;

public record DatosDetalleTipoDeBoleta(
        Long id,
        String perfil,
        int cantidad,
        Long idEvento
) {
    public DatosDetalleTipoDeBoleta (TipoDeBoleta tipoDeBoleta){
        this(tipoDeBoleta.getId(), tipoDeBoleta.getPerfil(), tipoDeBoleta.getCantidad(), tipoDeBoleta.getEvento().getId());
    }
}
