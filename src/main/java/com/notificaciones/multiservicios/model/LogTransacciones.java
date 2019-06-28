/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.notificaciones.multiservicios.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 *
 * @author David
 */
@Entity
@Table(name = "logtransacciones")
public class LogTransacciones extends DateAudit implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_log;
    
   @NotBlank
    private String datosEntrada;
    
    @NotBlank
    private String datosSalida;
    
    
    @Size(max = 100)
    private String codigoUUID;
    
    
    @Size(max = 2)
    private String estado;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente", referencedColumnName = "id_cliente")
    private Cliente cliente;
    
     
    @Size(max = 30)
    private String fecha_notificacion;

    public LogTransacciones() {
    }

    public String getFecha_notificacion() {
        return fecha_notificacion;
    }

    public void setFecha_notificacion(String fecha_notificacion) {
        this.fecha_notificacion = fecha_notificacion;
    }

    
    
    public Long getId_log() {
        return id_log;
    }

    public void setId_log(Long id_log) {
        this.id_log = id_log;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    
    
    
}
