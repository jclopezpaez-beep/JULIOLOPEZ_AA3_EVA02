/**
 * Archivo: LoginServlet.java
 * Paquete: com.telep.servlet
 * Proyecto: TELEP_APP
 * Descripción: Servlet encargado de gestionar la autenticación
 *              de usuarios del sistema. Valida credenciales,
 *              crea la sesión y almacena información del usuario
 *              para el control de acceso.
 * Tipo: Controlador de Autenticación (Servlet)
 * Autor: Julio Lopez
 */
package com.telep.servlet;

import com.telep.dao.UsuarioDAO;
import com.telep.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
// Mapea el servlet a la ruta de autenticación del sistema.
@WebServlet("/login")
// Controlador encargado del proceso de login.
// Interactúa con la capa DAO para validar credenciales.
public class LoginServlet extends HttpServlet {
// Instancia del DAO utilizada para validar
// las credenciales del usuario contra la base de datos.
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
// Se inicializa el DAO una sola vez cuando el servlet
// es cargado en memoria por el contenedor.
        usuarioDAO = new UsuarioDAO();
    }
// Procesa la solicitud POST enviada desde el formulario de login.
// Captura credenciales y valida autenticación.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

// Captura los datos enviados desde el formulario de autenticación.
        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");

// Valida las credenciales contra la base de datos
// utilizando el método login del DAO.
        Usuario u = usuarioDAO.login(usuario, password);
// Si el usuario es válido, se procede a crear sesión.
        if (u != null) {
// Login exitoso: se crea o reutiliza la sesión actual.
            HttpSession session = request.getSession();

// Se almacena el objeto Usuario completo en sesión
// para acceder a sus datos en distintas vistas.
            session.setAttribute("usuarioLogueado", u);

// Se almacena el rol del usuario por separado
// para facilitar validaciones rápidas en filtros.
            session.setAttribute("rolId", u.getRolId());

// Define el rol activo en sesión.
// Puede ser útil para funcionalidades dinámicas.
            session.setAttribute("rolActivo", u.getRolId());

 // Redirige al dashboard principal después del login exitoso.
            response.sendRedirect(request.getContextPath() + "/dashboard.jsp");

        } else {
// Login fallido: redirige nuevamente al login
// enviando un parámetro para mostrar mensaje de error.
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=1");
        }
    }
}