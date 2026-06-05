package com.ingeniawork.ticketflow.domain.tipoboleta;


import com.ingeniawork.ticketflow.domain.evento.Evento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name="tipo_boletas")
@Entity(name="TipoDeBoleta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TipoDeBoleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String perfil;
    private int cantidad;
    private BigDecimal costo;
    private boolean activo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id")
    private Evento evento;


    public TipoDeBoleta(DatosRegistroTipoDeBoleta datosRegistroTipoDeBoleta, Evento evento) {
        this.perfil = datosRegistroTipoDeBoleta.perfil();
        this.cantidad = datosRegistroTipoDeBoleta.cantidad();
        this.costo = datosRegistroTipoDeBoleta.costo();
        this.activo = true;
        this.evento = evento;
    }
}
