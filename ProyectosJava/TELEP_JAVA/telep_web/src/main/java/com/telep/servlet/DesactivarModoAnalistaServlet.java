/**
 * Archivo: DesactivarModoAnalistaServlet.java
 * Paquete: com.telep.servlet
 * Proyecto: TELEP_APP
 * Descripción: Servlet encargado de desactivar el modo Analista
 *              previamente activado por un Administrador.
 *              Elimina el atributo de sesión "modoAnalista"
 *              restaurando el comportamiento normal del rol.
 * Tipo: Controlador (Servlet)
 * Autor: Julio Lopez
 */

package com.telep.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
// Mapea el servlet para desactivar el modo analista.
// Se ejecuta mediante solicitud GET.
@WebServlet("/desactivarModoAnalista")
// Controlador encargado de eliminar el atributo
// de sesión que habilita el modo analista.
public class DesactivarModoAnalistaServlet extends HttpServlet {
// Procesa la solicitud GET para desactivar
// el modo analista en la sesión actual.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {    
// Obtiene la sesión actual sin crear una nueva.
// Si no existe sesión, el usuario no está autenticado.      
        HttpSession session = request.getSession(false);

// Si no existe sesión válida, se redirige al login
// para evitar acceso no autorizado.
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

// Se elimina el atributo de sesión "modoAnalista",
// desactivando el comportamiento temporal de analista.
        session.removeAttribute("modoAnalista");

// Redirige al dashboard.
// A partir de este punto, la interfaz deberá
// comportarse según el rol original del usuario.
        response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
    }
}