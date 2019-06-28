/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.notificaciones.multiservicios.repository;

import com.notificaciones.multiservicios.model.Parametros;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author David
 */
public interface ParametrosRepository extends JpaRepository<Parametros, Long>{
 
    @Query("SELECT p FROM Parametros p WHERE p.servicio.id_servicio=:id_servicio ")
    List<Parametros> findByServicio(@Param("id_servicio") Long id_servicio);
    
    
}
