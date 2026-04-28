package com.ingeniawork.ticketflow.domain.evento;

import com.ingeniawork.ticketflow.domain.boleta.Boleta;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Table(name="eventos")
@Entity(name="Evento")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String ciudad;
    private String direccion;
    @Enumerated(EnumType.STRING)
    private EstadoEvento estadoEvento;
    @Column(name = "fecha_inicio_evento")
    private LocalDateTime fechaInicialDelEvento;
    @Column(name = " fecha_final_evento")
    private LocalDateTime fechaFinalDelEvento;
    private boolean activo;

    public Evento(DatosRegistroEvento datosRegistroEvento) {
        this.nombre = datosRegistroEvento.nombre();
        this.ciudad = datosRegistroEvento.ciudad();
        this.direccion = datosRegistroEvento.direccion();
        this.estadoEvento = EstadoEvento.PUBLICADO;
        this.fechaInicialDelEvento = datosRegistroEvento.fechaInicialDelEvento();
        this.fechaFinalDelEvento = datosRegistroEvento.fechaFinalDelEvento();
        this.activo = true;
    }

    public void actualizarEvento(@Valid DatosActualizacionEvento datos){
        if(nombre != null){
            this.nombre = datos.nombre();
        }

        if(ciudad != null){
            this.ciudad = datos.ciudad();
        }

        if(direccion != null){
            this.direccion = datos.direccion();
        }

        if(fechaInicialDelEvento != null){
            this.fechaInicialDelEvento = datos.fechaInicialDelEvento();
        }

        if(fechaFinalDelEvento != null){
            this.fechaFinalDelEvento = datos.fechaFinalDelEvento();
        }

    };

    public void eliminar(){
        this.activo = false;
        this.estadoEvento = EstadoEvento.BORRADO;
    }




}
