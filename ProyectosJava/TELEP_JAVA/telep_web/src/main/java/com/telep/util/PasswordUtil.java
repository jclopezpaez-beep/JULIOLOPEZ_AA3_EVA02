/**
 * Archivo: PasswordUtil.java
 * Paquete: com.telep.util
 * Proyecto: TELEP_APP
 * Descripción: Clase utilitaria encargada de generar
 *              y verificar hashes de contraseñas utilizando
 *              el algoritmo SHA-256.
 *              Se utiliza para proteger las credenciales
 *              almacenadas en la base de datos.
 * Tipo: Utilidad de Seguridad
 * Autor: Julio Lopez
 */
package com.telep.util;

import java.security.MessageDigest;
// Clase utilitaria estática para operaciones
// relacionadas con seguridad de contraseñas.
public class PasswordUtil {

// ============================
// GENERACIÓN DE HASH SHA-256
// Convierte una contraseña en texto plano
// a una representación hexadecimal segura.
// ============================
    public static String hashPassword(String password) {
        try {
// Se obtiene instancia del algoritmo SHA-256
// proporcionado por la API de seguridad de Java.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
// Se transforma la contraseña en bytes (UTF-8)
// y se genera el hash criptográfico.
            byte[] hashBytes = md.digest(password.getBytes("UTF-8"));
// Se construye la representación hexadecimal
// del arreglo de bytes generado.
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
// Retorna el hash final en formato hexadecimal.
            return sb.toString();

        } catch (Exception e) {
// En caso de error en el algoritmo,
// se lanza excepción en tiempo de ejecución.
            throw new RuntimeException("Error al generar hash", e);
        }
    }

// ============================
// VERIFICACIÓN DE CONTRASEÑA
// Compara la contraseña ingresada en texto plano
// con el hash almacenado en base de datos.
// ============================
    public static boolean checkPassword(String passwordPlano, String hashBD) {
// Se genera el hash de la contraseña ingresada.
        String hashIngresado = hashPassword(passwordPlano);
// Se compara el hash generado con el hash almacenado.
// Retorna true si coinciden.
        return hashIngresado.equals(hashBD);
    }
}