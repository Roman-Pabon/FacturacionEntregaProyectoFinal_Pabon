package com.tienda.repository;

import com.tienda.modelo.Comprobante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ComprobanteRepository extends JpaRepository<Comprobante, Long> {

    // Método para buscar comprobantes por fecha
    List<Comprobante> findByFecha(Date fecha);

    // Método para buscar comprobantes por cliente
    List<Comprobante> findByClienteId(Long clienteId);

    // Método para buscar comprobantes por total
    List<Comprobante> findByTotalGreaterThan(double total);

    // Consulta personalizada para obtener el total de ventas en un rango de fechas
    @Query("SELECT SUM(c.total) FROM Comprobante c WHERE c.fecha BETWEEN :fechaInicio AND :fechaFin")
    Double obtenerTotalVentasEntreFechas(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
}

