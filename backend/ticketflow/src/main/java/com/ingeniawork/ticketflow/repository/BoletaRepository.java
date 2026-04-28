package com.ingeniawork.ticketflow.repository;

import com.ingeniawork.ticketflow.domain.boleta.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoletaRepository extends JpaRepository<Boleta, Long> {
}
