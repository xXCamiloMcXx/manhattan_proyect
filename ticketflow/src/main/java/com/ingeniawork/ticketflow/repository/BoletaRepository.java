package com.ingeniawork.ticketflow.repository;

import com.ingeniawork.ticketflow.domain.boleta.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoletaRepository extends JpaRepository<Boleta, Long> {
    Boleta findByToken(String token);

    List<Boleta> findByUsuarioId(Long usuarioId);
}
