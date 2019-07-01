/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.notificaciones.multiservicios.repository;

import com.notificaciones.multiservicios.model.Valores;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author David
 */
public interface ValoresRepository extends JpaRepository<Valores, Long>{
    @Query("SELECT v FROM Valores v WHERE v.servicio.id_servicio=:id_servicio and v.cliente.id_cliente=:id_cliente")
    List<Valores> getValoresbyClienteServicio(@Param("id_servicio") Long id_servicio,@Param("id_cliente") Long id_cliente);
}
