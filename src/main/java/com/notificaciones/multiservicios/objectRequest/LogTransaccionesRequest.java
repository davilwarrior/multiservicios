/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.notificaciones.multiservicios.objectRequest;


/**
 *
 * @author David
 */

public class LogTransaccionesRequest {
    
    private String datosEntrada;
    
   
    private String datosSalida;
    
   
    private String codigoUUID;
    
    
    private String estado;
    
    
    private Long user;

    public LogTransaccionesRequest() {
    }

    public String getDatosEntrada() {
        return datosEntrada;
    }

    public void setDatosEntrada(String datosEntrada) {
        this.datosEntrada = datosEntrada;
    }

    public String getDatosSalida() {
        return datosSalida;
    }

    public void setDatosSalida(String datosSalida) {
        this.datosSalida = datosSalida;
    }

    public String getCodigoUUID() {
        return codigoUUID;
    }

    public void setCodigoUUID(String codigoUUID) {
        this.codigoUUID = codigoUUID;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }
    
    
}
