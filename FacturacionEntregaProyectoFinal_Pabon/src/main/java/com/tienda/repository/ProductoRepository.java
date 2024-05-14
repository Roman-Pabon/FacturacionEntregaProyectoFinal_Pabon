package com.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tienda.modelo.Producto;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByPrecioGreaterThan(double precioMinimo);
    List<Producto> findByStockLessThan(int cantidadMaxima);
}

