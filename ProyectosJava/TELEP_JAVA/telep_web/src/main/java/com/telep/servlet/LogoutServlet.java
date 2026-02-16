/**
 * Archivo: LogoutServlet.java
 * Paquete: com.telep.servlet
 * Proyecto: TELEP_APP
 * Descripción: Servlet encargado de cerrar la sesión del usuario.
 *              Invalida la sesión actual y redirige al login.
 *              Forma parte del sistema de seguridad del aplicativo.
 * Tipo: Controlador de Autenticación (Servlet)
 * Autor: Julio Lopez
 */
package com.telep.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
// Mapea el servlet a la ruta encargada de finalizar la sesión.
@WebServlet("/logout")
// Controlador responsable de destruir la sesión activa
// y limpiar los datos almacenados en ella.
public class LogoutServlet extends HttpServlet {
// Procesa la solicitud GET para cerrar la sesión del usuario.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
// Obtiene la sesión actual sin crear una nueva.
// Si no existe sesión, significa que el usuario ya estaba desconectado.
        HttpSession session = request.getSession(false);
// Si existe sesión activa, se invalida completamente.
// Esto elimina todos los atributos almacenados.
        if (session != null) {
            session.invalidate();
        }

// Redirige al formulario de login después de cerrar sesión.
// Se utiliza redirect para forzar nueva petición HTTP.
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}