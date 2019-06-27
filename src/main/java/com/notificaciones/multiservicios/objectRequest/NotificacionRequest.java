/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.notificaciones.multiservicios.objectRequest;

import java.util.HashMap;

/**
 *
 * @author David
 */
public class NotificacionRequest {
    String cedula;
    String empresa;
    String servicio;
    String fecha_notificacion;
    HashMap<String, Object> info_adicional;

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getFecha_notificacion() {
        return fecha_notificacion;
    }

    public void setFecha_notificacion(String fecha_notificacion) {
        this.fecha_notificacion = fecha_notificacion;
    }

    public HashMap<String, Object> getInfo_adicional() {
        return info_adicional;
    }

    public void setInfo_adicional(HashMap<String, Object> info_adicional) {
        this.info_adicional = info_adicional;
    }
    
    
}
