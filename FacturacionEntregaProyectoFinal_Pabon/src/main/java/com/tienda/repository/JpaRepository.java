package com.tienda.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tienda.modelo.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}


