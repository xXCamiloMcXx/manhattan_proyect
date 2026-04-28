package com.ingeniawork.ticketflow.controller;

import com.ingeniawork.ticketflow.domain.evento.DatosDetalleEvento;
import com.ingeniawork.ticketflow.domain.evento.DatosRegistroEvento;
import com.ingeniawork.ticketflow.domain.evento.Evento;
import com.ingeniawork.ticketflow.repository.EventoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    EventoRepository eventoRepository;

    @Transactional
    @PostMapping
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroEvento datosRegistroEvento, HttpServletRequest request){
        var evento = new Evento(datosRegistroEvento);
        eventoRepository.save(evento);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar (@PathVariable Long id){
        var evento = eventoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetalleEvento(evento));
    }

    @GetMapping()
    public ResponseEntity listar (@PageableDefault(size = 5, sort = {"fecha_inicio_evento"}) Pageable paginacion){
        var eventos = eventoRepository.findByActivoTrue(paginacion)
                .map(DatosDetalleEvento::new);
        return ResponseEntity.ok(eventos);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminar (@PathVariable Long id){
        var eventos = eventoRepository.findById(id);

        return ResponseEntity.noContent().build();
    }




}
