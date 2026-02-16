/**
 * Archivo: ListarRegistrosAnalistaServlet.java
 * Paquete: com.telep.servlet
 * Proyecto: TELEP_APP
 * Descripción: Servlet encargado de consultar y mostrar
 *              los registros asociados al analista autenticado.
 *              Obtiene los datos desde la base de datos y
 *              los envía a la vista JSP correspondiente.
 * Tipo: Controlador (Servlet)
 * Autor: Julio Lopez
 */
package com.telep.servlet;

import com.telep.dao.RegistroAnalistaDAO;
import com.telep.model.RegistroAnalista;
import com.telep.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
// Mapea el servlet a la ruta del módulo analista.
// Acceso protegido por el AnalistaFilter.
@WebServlet("/analista/listar-registros")
// Controlador encargado de listar los registros
// creados por el analista autenticado.
public class ListarRegistrosAnalistaServlet extends HttpServlet {
// Instancia del DAO utilizada para acceder
// a los registros almacenados en base de datos.
    private RegistroAnalistaDAO dao;
// Método de inicialización del servlet.
// Se ejecuta una sola vez al cargar el servlet,
// permitiendo instanciar el DAO.
    @Override
    public void init() {
        dao = new RegistroAnalistaDAO();
    }
// Procesa la solicitud GET para consultar
// los registros del analista autenticado.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
// Obtiene la sesión actual sin crear una nueva.
// Si no existe sesión, el usuario no está autenticado.
        HttpSession session = request.getSession(false);
// Recupera el objeto Usuario almacenado en sesión
// durante el proceso de autenticación.
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

// SEGURIDAD
// ============================
// VALIDACIÓN DE SEGURIDAD
// Verifica que exista un usuario autenticado.
// ============================
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

// CONSULTAR RESTISTROS DE ANALISTA
// Consulta en base de datos los registros
// asociados al ID del analista autenticado.
        List<RegistroAnalista> registros = dao.listarPorUsuario(usuario.getId());

// EENVIAR AL JSP
// Se adjunta la lista de registros como atributo
// del request para ser utilizada en la vista JSP.
        request.setAttribute("registros", registros);

// REDIRIGIR A LA VISTA
// Se realiza forward hacia la vista JSP.
// Se utiliza forward (no redirect) para conservar
// los atributos del request.
        request.getRequestDispatcher("/analista/listar_registros.jsp")
               .forward(request, response);
    }
}