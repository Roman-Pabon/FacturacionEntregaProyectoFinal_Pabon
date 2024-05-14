package com.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tienda.modelo.Venta;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByFechaBetween(Date fechaInicio, Date fechaFin);
    List<Venta> findByTotalGreaterThan(double totalMinimo);
}

