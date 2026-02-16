/**
 * Archivo: CambiarEstadoUsuarioServlet.java
 * Paquete: com.telep.servlet
 * Proyecto: TELEP_APP
 * Descripción: Servlet encargado de activar o desactivar
 *              usuarios del sistema desde el módulo administrativo.
 *              Realiza una actualización lógica del estado del usuario.
 * Tipo: Controlador (Servlet)
 * Autor: Julio Lopez
 */
package com.telep.servlet;

import com.telep.dao.UsuarioDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

// Mapeo del servlet para gestionar cambios de estado de usuarios.
// Ruta protegida por el filtro de administrador.
@WebServlet("/admin/cambiar-estado-usuario")
// Controlador que permite cambiar el estado lógico
// (activo / inactivo) de un usuario en el sistema.
public class CambiarEstadoUsuarioServlet extends HttpServlet {
// Procesa la solicitud GET para cambiar el estado de un usuario.
// Recibe parámetros desde la vista administrativa.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
// Obtiene la sesión actual para almacenar mensajes
// que serán mostrados en la vista mediante notificaciones tipo Toast.
        HttpSession session = request.getSession();
// Se encapsula la operación en un bloque try-catch
// para manejar posibles errores de conversión o acceso a datos.
        try {
// Se obtienen los parámetros enviados desde la vista:
// - id: identificador del usuario
// - estado: nuevo estado lógico (activo / inactivo)
            int id = Integer.parseInt(request.getParameter("id"));
            String estado = request.getParameter("estado");
// Se crea instancia del DAO para delegar la actualización
// en la capa de persistencia.
            UsuarioDAO dao = new UsuarioDAO();
// Se ejecuta la actualización del estado en base de datos.
            dao.cambiarEstado(id, estado);

// Se define mensaje dinámico dependiendo del nuevo estado aplicado.
            if ("activo".equals(estado)) {
                session.setAttribute("msg", "Usuario activado correctamente");
                session.setAttribute("msgType", "success");
            } else {
                session.setAttribute("msg", "Usuario desactivado correctamente");
                session.setAttribute("msgType", "warning");
            }

        } catch (Exception e) {
// Manejo de excepción general.
// Puede producirse por error de conversión o fallo en base de datos.
            e.printStackTrace();
            session.setAttribute("msg", "Error al cambiar estado");
            session.setAttribute("msgType", "danger");
        }
// Redirige nuevamente al listado de usuarios.
// La vista interpretará los atributos "msg" y "msgType"
// para mostrar la notificación correspondiente.
        response.sendRedirect(request.getContextPath() + "/admin/usuarios.jsp");
    }
}