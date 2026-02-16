/**
 * Archivo: DBConnection.java
 * Paquete: com.telep.config
 * Proyecto: TELEP_APP
 * Descripción: Clase encargada de gestionar la conexión a la base de datos MySQL
 *              utilizando JDBC. Centraliza los parámetros de conexión y
 *              proporciona un método estático reutilizable para obtener conexiones.
 * Autor: [Julio Lopez]
 */

package com.telep.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Clase utilitaria que encapsula la configuración de conexión a la base de datos.
// Permite obtener conexiones JDBC desde cualquier parte del sistema.

public class DBConnection {

// URL de conexión JDBC hacia la base de datos MySQL.
// Incluye configuración de zona horaria, codificación UTF-8,
// desactivación de SSL y recuperación de clave pública.

    private static final String URL =
            "jdbc:mysql://localhost:3307/telep_app_java"
            + "?useSSL=false"
            + "&serverTimezone=UTC"
            + "&allowPublicKeyRetrieval=true"
            + "&useUnicode=true"
            + "&characterEncoding=UTF-8";

 // Usuario de la base de datos con permisos definidos para la aplicación.

    private static final String USER = "telep_java";

// Contraseña del usuario de base de datos.

    private static final String PASS = "Java1234";

// Método estático que retorna una nueva conexión activa a la base de datos.
// Lanza SQLException en caso de error durante la conexión.

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}