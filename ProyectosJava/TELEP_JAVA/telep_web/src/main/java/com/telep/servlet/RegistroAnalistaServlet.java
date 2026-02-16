/**
 * Archivo: RegistroAnalistaServlet.java
 * Paquete: com.telep.servlet
 * Proyecto: TELEP_APP
 * Descripción: Servlet encargado de registrar nuevos
 *              registros técnicos realizados por el analista.
 *              Asocia el registro al usuario autenticado
 *              y lo persiste en la base de datos.
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
// Mapea el servlet para procesar el formulario
// de creación de registros del analista.
@WebServlet("/registroAnalista")
// Controlador encargado de gestionar la creación
// de registros asociados al analista autenticado.
public class RegistroAnalistaServlet extends HttpServlet {
// Instancia del DAO utilizada para persistir
// los registros del analista.
    private RegistroAnalistaDAO dao;
// Método de inicialización del servlet.
// Se ejecuta una sola vez cuando el contenedor lo carga,
// permitiendo instanciar el DAO.
    @Override
    public void init() {
        dao = new RegistroAnalistaDAO();
    }
// Procesa la solicitud POST enviada desde el formulario
// de registro del módulo analista.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
// Obtiene la sesión actual sin crear una nueva.
// Si no existe sesión, el usuario no está autenticado.
        HttpSession session = request.getSession(false);
// Recupera el usuario autenticado almacenado en sesión.
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
// Validación de seguridad:
// Si no existe usuario en sesión, redirige al login.
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
// Se construye el objeto RegistroAnalista con los datos
// enviados desde el formulario.
        RegistroAnalista r = new RegistroAnalista();
// Se asocia el registro al usuario autenticado.
// Esto garantiza que cada analista solo gestione sus propios registros.
        r.setIdUsuario(usuario.getId());
        r.setCliente(request.getParameter("cliente"));
        r.setEquipo(request.getParameter("equipo"));
        r.setModelo(request.getParameter("modelo"));
        r.setRam(request.getParameter("ram"));
        r.setAlmacenamiento(request.getParameter("almacenamiento"));
        r.setSerial(request.getParameter("serial"));
        r.setObservaciones(request.getParameter("observaciones"));
// Se delega la inserción del registro a la capa DAO.
        boolean ok = dao.insertar(r);
// Se define mensaje de resultado según el éxito
// o fallo en la operación de persistencia.
        if (ok) {
            // TOAST EXITO
            session.setAttribute("msg", "Registro guardado correctamente");
            session.setAttribute("msgType", "success");
        } else {
            // TOAST ERROR
            session.setAttribute("msg", "Error al guardar el registro");
            session.setAttribute("msgType", "danger");
        }

// Redirige nuevamente al formulario.
// Se aplica el patrón PRG (Post/Redirect/Get)
// para evitar reenvío del formulario.
        response.sendRedirect(request.getContextPath() + "/analista/registro.jsp");
    }
}