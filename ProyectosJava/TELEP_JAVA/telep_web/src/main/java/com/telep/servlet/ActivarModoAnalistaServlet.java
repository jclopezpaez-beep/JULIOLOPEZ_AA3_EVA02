/**
 * Archivo: ActivarModoAnalistaServlet.java
 * Paquete: com.telep.servlet
 * Proyecto: TELEP_APP
 * Descripción: Servlet encargado de permitir que un usuario con rol
 *              Administrador active temporalmente el "modo Analista"
 *              dentro de su sesión actual.
 *              Este modo se gestiona mediante un atributo de sesión.
 * Tipo: Controlador (Servlet)
 * Autor: Julio Lopez
 */

package com.telep.servlet;

import com.telep.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
// Mapea este servlet a la ruta /activar-modo-analista.
// Permite ejecutar la activación del modo analista mediante solicitud GET.
@WebServlet("/activar-modo-analista")
// Servlet que maneja la activación de un modo especial en la sesión
// para permitir al administrador operar como analista.
public class ActivarModoAnalistaServlet extends HttpServlet {
// Método que procesa la solicitud GET.
// Valida sesión, verifica rol y activa el modo analista si corresponde.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
// Obtiene la sesión actual sin crear una nueva.
// Si no existe sesión, el usuario no está autenticado.
        HttpSession session = request.getSession(false);
// Si no existe sesión activa, se redirige al login.
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
// Se obtiene el objeto Usuario almacenado en sesión
// al momento de la autenticación.
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
// Si no existe usuario autenticado en sesión,
/// se redirige nuevamente al login.
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

// ============================
// VALIDACIÓN DE ROL
// Solo el usuario con rol Administrador (rolId = 1)
// puede activar el modo analista.
// ============================
        if (usuario.getRolId() == 1) {
// Se activa el atributo de sesión "modoAnalista",
// permitiendo que el administrador opere temporalmente
// bajo funcionalidades destinadas al rol Analista.
            session.setAttribute("modoAnalista", true);
        }
// Redirige al dashboard después de intentar activar el modo.
// La vista deberá interpretar el atributo "modoAnalista".
        response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
    }
}