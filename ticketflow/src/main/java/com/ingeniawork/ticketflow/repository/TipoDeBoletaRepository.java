package com.ingeniawork.ticketflow.repository;

import com.ingeniawork.ticketflow.domain.tipoboleta.TipoDeBoleta;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface TipoDeBoletaRepository extends JpaRepository<TipoDeBoleta,Long> {
    List<TipoDeBoleta> findByEventoId(Long eventoId);
}
