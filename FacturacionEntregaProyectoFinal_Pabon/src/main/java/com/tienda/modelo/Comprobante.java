package com.tienda.modelo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.tienda.repository.ClienteRepository;
import com.tienda.repository.ProductoRepository;
import com.tienda.repository.ComprobanteRepository;

@Service
public class Comprobante {
	private Cliente cliente;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    @Transactional
    public Comprobante generarComprobante(Comprobante comprobante) {
        // Verificar si el cliente existe
        Cliente cliente = clienteRepository.findById(comprobante.getCliente().getId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        List<Producto> productos = new ArrayList<>();
        double total = 0.0;
        for (DetalleComprobante detalle : comprobante.getDetalles()) {
            // Verificar si el producto existe
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

            // Verificar si la cantidad solicitada es menor o igual que el stock disponible
            if (detalle.getCantidad() > producto.getStock()) {
                throw new IllegalArgumentException("Cantidad solicitada mayor que el stock disponible para el producto " + producto.getNombre());
            }

            total += producto.getPrecio() * detalle.getCantidad();
            productos.add(producto);
        }

        comprobante.setCliente(cliente);
        comprobante.setProductos(productos);
        comprobante.setTotal(total);

        // Reducir el stock de productos vendidos
        for (DetalleComprobante detalle : comprobante.getDetalles()) {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);
        }

        // Guardar el comprobante en la base de datos
        return comprobanteRepository.save(comprobante);
    }
}
