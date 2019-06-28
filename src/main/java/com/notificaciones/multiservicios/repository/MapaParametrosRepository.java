/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.notificaciones.multiservicios.repository;

import com.notificaciones.multiservicios.model.MapaParametros;
import com.notificaciones.multiservicios.model.Parametros;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author David
 */
public interface MapaParametrosRepository extends JpaRepository<MapaParametros, Long> {

    @Query("SELECT p FROM MapaParametros p WHERE p.parametro.id_param =:id_mparam ")
    List<MapaParametros> findByParametro(@Param("id_mparam") Long id_mparam);
}
