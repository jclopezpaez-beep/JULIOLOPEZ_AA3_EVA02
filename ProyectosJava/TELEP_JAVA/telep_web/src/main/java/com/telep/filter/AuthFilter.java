/**
 * Archivo: AuthFilter.java
 * Paquete: com.telep.filter
 * Proyecto: TELEP_APP
 * Descripción: Filtro global de autenticación encargado de proteger
 *              todas las rutas del sistema.
 *              Define las rutas públicas y valida la existencia
 *              de una sesión activa para el resto de solicitudes.
 * Tipo: Filtro de autenticación global.
 * Autor: Julio Lopez
 */
package com.telep.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

// Aplica este filtro a todas las rutas del sistema.
// Funciona como mecanismo principal de autenticación.
@WebFilter("/*")
// Implementa la interfaz Filter para interceptar todas las
// solicitudes HTTP antes de que lleguen a los controladores o vistas.
public class AuthFilter implements Filter {
// Método principal del filtro.
// Se ejecuta en cada solicitud HTTP para validar
// si la ruta es pública o requiere autenticación.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
// Conversión de los objetos genéricos a objetos HTTP
// para poder acceder a información de sesión y URI.
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
// Se obtiene la URI solicitada y el contexto de la aplicación
// para poder evaluar si la ruta es pública o protegida.
        String uri = req.getRequestURI();
        String context = req.getContextPath();

// RUTAS PUBLICAS
// ============================
// BLOQUE: RUTAS PÚBLICAS
// Descripción: Define las rutas que pueden ser accedidas
// sin necesidad de autenticación.
// Incluye login, logout, recursos estáticos y página principal.
// ============================
        if (
            uri.equals(context + "/") ||
            uri.equals(context + "/login.jsp") ||
            uri.equals(context + "/login") ||
            uri.equals(context + "/logout") ||
            uri.startsWith(context + "/css") ||
            uri.startsWith(context + "/js") ||
            uri.startsWith(context + "/images")
        ) {
// Permite continuar sin validar sesión,
/// ya que la ruta es pública.
            chain.doFilter(request, response);
            return;
        }

// VALIDAR SESION
// ============================
// BLOQUE: VALIDACIÓN DE SESIÓN
// Descripción: Verifica si el usuario tiene sesión activa
// y atributo de autenticación válido.
// ============================

// Se obtiene la sesión actual sin crear una nueva.
// Si no existe sesión, retorna null.
        HttpSession session = req.getSession(false);
// Verifica que exista sesión activa y que contenga
// el atributo "usuarioLogueado", indicando autenticación válida.
        if (session != null && session.getAttribute("usuarioLogueado") != null) {
            chain.doFilter(request, response);
        } else {
// Redirige al login si el usuario no tiene sesión activa.
            res.sendRedirect(context + "/login.jsp");
        }
    }
}