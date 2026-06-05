package com.ingeniawork.ticketflow.domain.boleta;

import com.ingeniawork.ticketflow.domain.evento.Evento;
import com.ingeniawork.ticketflow.domain.tipoboleta.TipoDeBoleta;
import com.ingeniawork.ticketflow.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Table(name="boletas")
@Entity(name="Boleta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private String asiento;
    private boolean activo;

    @Column(name = "fecha_compra")
    private LocalDateTime fechaDeCompra;
    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaDeVencimiento;

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoBoleta estadoBoleta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_boleta_id")
    private TipoDeBoleta tipoDeBoleta;


    public Boleta(String token, Usuario usuario, TipoDeBoleta tipoDeBoleta, Evento evento, DatosCreacionBoleta datosCreacionBoleta) {
        this.token = token;
        this.asiento = datosCreacionBoleta.asiento();
        this.fechaDeCompra = LocalDateTime.now();
        this.fechaDeVencimiento = evento.getFechaFinalDelEvento();
        this.tipoDeBoleta = tipoDeBoleta;
        this.usuario = usuario;
        this.estadoBoleta = EstadoBoleta.PENDIENTE;
        this.activo = true;
    }




    public void ActualizarEstado(Boleta boleta){
        if(estadoBoleta != null && estadoBoleta != EstadoBoleta.PENDIENTE){
            estadoBoleta = EstadoBoleta.USADO;
        }
    }




}
