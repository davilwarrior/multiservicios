/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.notificaciones.multiservicios.repository;

import com.notificaciones.multiservicios.model.LogTransacciones;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author David
 */
public interface LogTransaccionesRepository extends JpaRepository<LogTransacciones, Long>{

    
    
}
