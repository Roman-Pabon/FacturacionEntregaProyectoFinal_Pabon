package com.tienda.repository;

import com.tienda.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Método para buscar clientes por nombre
    List<Cliente> findByNombre(String nombre);

    // Método para buscar clientes por edad mayor que
    List<Cliente> findByEdadGreaterThan(int edad);

    // Consulta personalizada para obtener clientes por dirección
    @Query("SELECT c FROM Cliente c WHERE c.direccion = :direccion")
    List<Cliente> buscarPorDireccion(@Param("direccion") String direccion);
}

