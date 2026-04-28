package com.ingeniawork.ticketflow.controller;


import com.ingeniawork.ticketflow.domain.evento.Evento;
import com.ingeniawork.ticketflow.domain.tipoboleta.DatosDetalleTipoDeBoleta;
import com.ingeniawork.ticketflow.domain.tipoboleta.DatosRegistroTipoDeBoleta;
import com.ingeniawork.ticketflow.domain.tipoboleta.TipoDeBoleta;
import com.ingeniawork.ticketflow.repository.EventoRepository;
import com.ingeniawork.ticketflow.repository.TipoDeBoletaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/boletas/categoria")
public class TipoDeBoletaController {

    @Autowired
    EventoRepository eventoRepository;

    @Autowired
    TipoDeBoletaRepository tipoDeBoletaRepository;

    @Transactional
    @PostMapping
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroTipoDeBoleta datosRegistroTipoDeBoleta){
        Evento evento = eventoRepository.getReferenceById(datosRegistroTipoDeBoleta.idEvento());
        TipoDeBoleta tipoDeBoleta = new TipoDeBoleta(datosRegistroTipoDeBoleta, evento);
        tipoDeBoletaRepository.save(tipoDeBoleta);
        return ResponseEntity.ok().build();
    };


    @GetMapping("/{id}")
    public ResponseEntity listarPerfiles(@PathVariable Long eventoId){
        List<DatosDetalleTipoDeBoleta> listaCategoria = tipoDeBoletaRepository.findByEventoId(eventoId).stream()
                .map(DatosDetalleTipoDeBoleta::new).collect(Collectors.toList());

        return ResponseEntity.ok(listaCategoria);
    }



}
