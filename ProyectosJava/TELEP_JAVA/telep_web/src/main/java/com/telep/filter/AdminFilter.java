/**
 * Archivo: AdminFilter.java
 * Paquete: com.telep.filter
 * Proyecto: TELEP_APP
 * Descripción: Filtro de seguridad encargado de restringir el acceso
 *              a las rutas administrativas (/admin/*).
 *              Verifica que el usuario tenga sesión activa y rol de administrador.
 * Tipo: Filtro de autorización basado en rol.
 * Autor: Julio Lopez
 */
package com.telep.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

// Aplica este filtro a todas las rutas que comiencen con /admin/
// Garantiza que solo usuarios con rol administrador puedan acceder.
@WebFilter("/admin/*")
// Implementa la interfaz Filter para interceptar solicitudes HTTP
// antes de que lleguen a los recursos protegidos.
public class AdminFilter implements Filter {

// Método principal del filtro.
// Se ejecuta antes de procesar cualquier solicitud hacia rutas /admin/*.
// Permite validar sesión y rol antes de continuar con la petición.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
// Se convierten los objetos genéricos a objetos HTTP
// para poder acceder a métodos específicos de sesión y redirección.
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
// Se obtiene la sesión actual sin crear una nueva.
// Si no existe sesión activa, retorna null.
        HttpSession session = req.getSession(false);
// Verifica que exista sesión activa y que contenga el atributo rolId.
// Si no cumple, se redirige al login.
        if (session == null || session.getAttribute("rolId") == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        int rolId = (int) session.getAttribute("rolId");
// Verifica que el rol sea administrador (rolId = 1).
// Si no es administrador, se redirige al dashboard general.
        if (rolId != 1) {
// Redirige al dashboard si el usuario no tiene permisos administrativos.
            res.sendRedirect(req.getContextPath() + "/dashboard.jsp");
            return;
        }
// Si todas las validaciones son correctas,
// permite que la solicitud continúe hacia el recurso solicitado.
        chain.doFilter(request, response);
    }
}
