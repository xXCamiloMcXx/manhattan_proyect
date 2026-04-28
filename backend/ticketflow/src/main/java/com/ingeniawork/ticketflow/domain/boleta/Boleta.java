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
    @Column(name = "fecha_compra")
    private LocalDateTime fechaDeCompra;
    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaDeVencimiento;
    private boolean activo;
    @Enumerated(EnumType.STRING)
    private EstadoBoleta estadoBoleta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_boleta_id")
    private TipoDeBoleta tipoDeBoleta;


    public Boleta(String token, Usuario usuario, TipoDeBoleta tipoDeBoleta, Evento evento) {
        this.token = token;
        this.fechaDeCompra = LocalDateTime.now();
        this.fechaDeVencimiento = evento.getFechaFinalDelEvento();
        this.tipoDeBoleta = tipoDeBoleta;
        this.usuario = usuario;
        this.estadoBoleta = EstadoBoleta.PENDIENTE;
        this.activo = true;
    }
}
