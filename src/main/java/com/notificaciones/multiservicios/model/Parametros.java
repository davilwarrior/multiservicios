/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.notificaciones.multiservicios.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 *
 * @author David
 */
@Entity
@Table(name = "parametros")
//@NamedQuery(name="as",query="SELECT v FROM Valores v WHERE v.servicio.id_servicio=1 and v.cliente.id_cliente ")
public class Parametros extends DateAudit implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_param;

    @NotBlank
    @Size(max = 40)
    private String campo;

    @NotBlank
    private EnumTipoEntradas tipo;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_servicio", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Servicio servicio;

    public Long getId_param() {
        return id_param;
    }

    public void setId_param(Long id_param) {
        this.id_param = id_param;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public EnumTipoEntradas getTipo() {
        return tipo;
    }

    public void setTipo(EnumTipoEntradas tipo) {
        this.tipo = tipo;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
    
    
    
    
}
