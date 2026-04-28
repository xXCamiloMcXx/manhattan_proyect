package com.ingeniawork.ticketflow.repository;

import com.ingeniawork.ticketflow.domain.evento.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    Page<Evento> findByActivoTrue(Pageable paginacion);
}
