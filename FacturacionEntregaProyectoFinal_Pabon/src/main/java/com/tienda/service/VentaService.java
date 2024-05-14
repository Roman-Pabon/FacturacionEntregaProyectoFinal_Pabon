package com.tienda.service;

import com.tienda.modelo.Venta;
import com.tienda.repositorio.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;

    @Autowired
    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    public List<Venta> obtenerTodasLasVentas() {
        return ventaRepository.findAll();
    }

    public Optional<Venta> obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id);
    }

    public Venta realizarVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    public void eliminarVentaPorId(Long id) {
        ventaRepository.deleteById(id);
    }
}
