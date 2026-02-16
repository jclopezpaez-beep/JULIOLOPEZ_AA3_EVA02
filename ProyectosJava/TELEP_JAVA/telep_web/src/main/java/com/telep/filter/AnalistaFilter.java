/**
 * Archivo: AnalistaFilter.java
 * Paquete: com.telep.filter
 * Proyecto: TELEP_APP
 * Descripción: Filtro de seguridad encargado de restringir el acceso
 *              a las rutas del módulo de analistas (/analista/*).
 *              Verifica que el usuario tenga sesión activa y rol de Analista.
 * Tipo: Filtro de autorización basado en rol.
 * Autor: Julio Lopez
 */
package com.telep.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

// Aplica este filtro a todas las rutas que comiencen con /analista/
// Garantiza que solo usuarios con rol Analista puedan acceder.

@WebFilter("/analista/*")

// Implementa la interfaz Filter para interceptar solicitudes HTTP
// dirigidas al módulo de analistas antes de que sean procesadas.
public class AnalistaFilter implements Filter {

// Método principal del filtro.
// Se ejecuta antes de procesar solicitudes hacia rutas /analista/*.
// Permite validar la sesión y el rol del usuario.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
// Conversión a objetos HTTP para poder manejar sesión y redirecciones.
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
// Se obtiene la sesión actual sin crear una nueva.
// Si no existe sesión, retorna null.
        HttpSession session = req.getSession(false);
// Verifica que exista sesión activa y que contenga el atributo rolId.
// Si no cumple, redirige al login.
        if (session == null || session.getAttribute("rolId") == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
// Obtiene el rol almacenado en la sesión del usuario.
        int rolId = (int) session.getAttribute("rolId");
// Verifica que el rol corresponda a Analista (rolId = 2).
// Si no coincide, redirige al dashboard general.
        if (rolId != 2) {
// Redirige al dashboard si el usuario no tiene permisos de analista.
            res.sendRedirect(req.getContextPath() + "/dashboard.jsp");
            return;
        }
// Si la validación es exitosa, permite continuar
// con el procesamiento normal de la solicitud.
        chain.doFilter(request, response);
    }
}