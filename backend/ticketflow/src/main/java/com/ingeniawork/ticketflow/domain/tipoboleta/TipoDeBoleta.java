package com.ingeniawork.ticketflow.domain.tipoboleta;


import com.ingeniawork.ticketflow.domain.evento.Evento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private boolean activo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id")
    private Evento evento;


    public TipoDeBoleta(DatosRegistroTipoDeBoleta datosRegistroTipoDeBoleta, Evento evento) {
        this.perfil = datosRegistroTipoDeBoleta.perfil();
        this.cantidad = datosRegistroTipoDeBoleta.cantidad();
        this.activo = true;
        this.evento = evento;
    }
}
