/**
 * Archivo: RegistroAnalista.java
 * Paquete: com.telep.model
 * Proyecto: TELEP_APP
 * Descripción: Clase modelo que representa la entidad RegistroAnalista.
 *              Mapea los campos de la tabla registros_analistas
 *              y encapsula los datos asociados a cada registro.
 * Tipo: POJO (Plain Old Java Object)
 * Autor: Julio Lopez
 */
package com.telep.model;

import java.time.LocalDateTime;
// Clase que representa un registro de configuración realizado por un analista.
// Contiene los atributos correspondientes a la tabla registros_analistas.
public class RegistroAnalista {
// ============================
// DATOS GENERALES DEL REGISTRO
// ============================

// Nombre de usuario asociado al registro.
    private int id;
    private int idUsuario;
    private String usuario;   
    private String cliente;   
    private String equipo;
    private String modelo;
    private String ram;
    private String almacenamiento;
    private String serial;
    private String observaciones;
    private String estadoRevision;
// ============================
// CAMPOS DE CONTROL Y AUDITORÍA
// ============================

// Fecha y hora en que se creó el registro.
    private LocalDateTime fechaRegistro;
    private LocalDateTime actualizadoEn;

// GETTERS Y SETTERS
// ============================
// MÉTODOS DE ACCESO (GETTERS Y SETTERS)
// ============================
// Permiten el acceso controlado a los atributos privados,
// manteniendo el principio de encapsulamiento.

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCliente() {
        return cliente;
    }
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEquipo() {
        return equipo;
    }
    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getRam() {
        return ram;
    }
    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getAlmacenamiento() {
        return almacenamiento;
    }
    public void setAlmacenamiento(String almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public String getSerial() {
        return serial;
    }
    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstadoRevision() {
        return estadoRevision;
    }
    public void setEstadoRevision(String estadoRevision) {
        this.estadoRevision = estadoRevision;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }
    public void setActualizadoEn(LocalDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }
}