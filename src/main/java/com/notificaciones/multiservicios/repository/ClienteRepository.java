/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.notificaciones.multiservicios.repository;

import com.notificaciones.multiservicios.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author David
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    
    @Query("")
    Cliente getClientexIDUsuario(Long idUsuario);
}
