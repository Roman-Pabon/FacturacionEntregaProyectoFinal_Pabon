package com.tienda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
public class ComprobanteService {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    public ResponseEntity<ComprobanteResponse> generarComprobante(ComprobanteRequest request) {
        // Obtener la fecha del servicio REST
        Date fecha = obtenerFechaDelServicio();
        if (fecha == null) {
            // En caso de fallo, calcular la fecha usando la clase Date de Java
            fecha = new Date();
        }

        // Validar cliente existente
        Cliente cliente = clienteService.obtenerClientePorId(request.getIdCliente());
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ComprobanteResponse("Cliente no encontrado"));
        }

        // Validar productos existentes y reducir stock
        List<DetalleComprobante> detalles = request.getDetalles();
        double total = 0.0;
        int cantidadProductos = 0;
        for (DetalleComprobante detalle : detalles) {
            Producto producto = productoService.obtenerProductoPorId(detalle.getIdProducto());
            if (producto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ComprobanteResponse("Producto no encontrado"));
            }
            if (detalle.getCantidad() > producto.getStock()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ComprobanteResponse("Cantidad solicitada mayor que el stock disponible para el producto " + producto.getNombre()));
            }
            total += producto.getPrecio() * detalle.getCantidad();
            cantidadProductos += detalle.getCantidad();
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoService.actualizarProducto(producto.getId(), producto);
        }

        // Generar comprobante
        Comprobante comprobante = new Comprobante();
        comprobante.setFecha(fecha);
        comprobante.setCliente(cliente);
        comprobante.setDetalles(detalles);
        comprobante.setTotal(total);
        comprobanteRepository.save(comprobante);

        // Crear y retornar la respuesta del comprobante generado
        ComprobanteResponse response = new ComprobanteResponse("Comprobante generado exitosamente", comprobante.getId());
        response.setFecha(fecha);
        response.setTotal(total);
        response.setCantidadProductos(cantidadProductos);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private Date obtenerFechaDelServicio() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://worldclockapi.com/api/json/utc/now";
            WorldClockResponse response = restTemplate.getForObject(url, WorldClockResponse.class);
            if (response != null && response.getCurrentDateTime() != null) {
                return response.getCurrentDateTime();
            }
        } catch (Exception e) {
            // Manejar la excepción
        }
        return null;
    }
}
