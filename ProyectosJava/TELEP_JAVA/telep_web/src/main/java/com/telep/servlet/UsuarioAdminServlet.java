/**
 * Archivo: UsuarioAdminServlet.java
 * Paquete: com.telep.servlet
 * Proyecto: TELEP_APP
 * Descripción: Servlet encargado de gestionar el módulo
 *              administrativo de usuarios. Permite listar
 *              usuarios y cambiar su estado (activo/inactivo).
 * Tipo: Controlador Administrativo (Servlet)
 * Autor: Julio Lopez
 */
package com.telep.servlet;

import com.telep.dao.UsuarioDAO;
import com.telep.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
// Mapea el servlet al módulo de administración de usuarios.
// Acceso restringido al rol Administrador.
@WebServlet("/admin/usuarios")
// Controlador principal del módulo de usuarios.
// Gestiona listado y cambios de estado.
public class UsuarioAdminServlet extends HttpServlet {
// Instancia del DAO utilizada para operaciones
// de consulta y actualización de usuarios.
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
// Procesa la solicitud GET para listar todos los usuarios.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

// ============================
// VALIDACIÓN DE SESIÓN
// Verifica que exista sesión activa.
// ============================
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("rolId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
// Recupera el rol almacenado en sesión.
        int rolId = (int) session.getAttribute("rolId");

// ============================
// VALIDACIÓN DE ROL
// Solo el rol Administrador (1)
// puede acceder a este módulo.
// ============================
        if (rolId != 1) {
            response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
            return;
        }

// Consulta todos los usuarios registrados
// en el sistema.
        List<Usuario> usuarios = usuarioDAO.listarTodos();
// Envía la lista de usuarios a la vista
// mediante atributo del request.
        request.setAttribute("usuarios", usuarios);

// Se realiza forward hacia la vista JSP.
// Se utiliza forward para conservar atributos del request.
        request.getRequestDispatcher("/admin/usuarios.jsp").forward(request, response);
    }
// Procesa la solicitud POST para cambiar el estado
// de un usuario (activar / desactivar).
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

 // Obtiene la acción enviada desde la vista
// y el identificador del usuario.
        String accion = request.getParameter("accion");
        int id = Integer.parseInt(request.getParameter("id"));
// Ejecuta el cambio de estado según la acción recibida.
        if ("activar".equals(accion)) {
            usuarioDAO.cambiarEstado(id, "activo");
        } else if ("desactivar".equals(accion)) {
            usuarioDAO.cambiarEstado(id, "inactivo");
        }
// Redirige nuevamente al módulo de usuarios.
// Se aplica el patrón PRG (Post/Redirect/Get).
        response.sendRedirect(request.getContextPath() + "/admin/usuarios");
    }
}