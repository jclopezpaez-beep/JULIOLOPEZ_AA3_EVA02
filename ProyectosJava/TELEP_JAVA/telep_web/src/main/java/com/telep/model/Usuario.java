/**
 * Archivo: Usuario.java
 * Paquete: com.telep.model
 * Proyecto: TELEP_APP
 * Descripción: Clase modelo que representa la entidad Usuario.
 *              Mapea los campos de la tabla usuarios y encapsula
 *              la información relacionada con autenticación,
 *              roles y estado del sistema.
 * Tipo: POJO (Plain Old Java Object)
 * Autor: Julio Lopez
 */
package com.telep.model;
// Clase que representa un usuario del sistema.
// Contiene información de autenticación, rol y estado.
public class Usuario {
// ============================
// IDENTIFICACIÓN DEL USUARIO
// ============================

// Identificador único del usuario en la base de datos.
    private int id;
// ============================
// DATOS PERSONALES Y DE ACCESO
// ============================

// Nombre de usuario utilizado para iniciar sesión.
    private String usuario;
    private String nombreCompleto;
    private String correo;
    private String passwordHash;
    private int rolId;
    private String rol; 
    private String estado;

// GETTERS Y SETTERS
// ============================
// MÉTODOS DE ACCESO (GETTERS Y SETTERS)
// ============================
// Permiten el acceso controlado a los atributos privados,
// respetando el principio de encapsulamiento.

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}