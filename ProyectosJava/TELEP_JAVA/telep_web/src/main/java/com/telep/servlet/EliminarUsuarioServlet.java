/**
 * Archivo: EliminarUsuarioServlet.java
 * Paquete: com.telep.servlet
 * Proyecto: TELEP_APP
 * Descripción: Servlet encargado de eliminar un usuario
 *              del sistema desde el módulo administrativo.
 *              Ejecuta una eliminación física en base de datos.
 * Tipo: Controlador (Servlet)
 * Autor: Julio Lopez
 */
package com.telep.servlet;

import com.telep.dao.UsuarioDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
// Mapea el servlet a la ruta protegida para eliminación de usuarios.
// Solo accesible por el rol Administrador.
@WebServlet("/admin/eliminar-usuario")
// Controlador que gestiona la eliminación de usuarios
// dentro del módulo administrativo.
public class EliminarUsuarioServlet extends HttpServlet {
// Procesa la solicitud GET para eliminar un usuario.
// Recibe el identificador desde la vista administrativa.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
// Mensaje de depuración utilizado durante pruebas.
// En entorno productivo debería reemplazarse por logging estructurado.
            System.out.println(">>> entro a eliminar usuario <<<");   
// Obtiene la sesión actual para almacenar mensajes
// que serán mostrados en la interfaz mediante notificaciones.
        HttpSession session = request.getSession();
// Se encapsula la operación en un bloque try-catch
// para manejar posibles errores de conversión o base de datos.
        try {
// Se obtiene el identificador del usuario a eliminar
// enviado como parámetro en la solicitud.
            int id = Integer.parseInt(request.getParameter("id"));
// Se instancia el DAO para delegar la eliminación
// en la capa de acceso a datos
            UsuarioDAO dao = new UsuarioDAO();
// Ejecuta la eliminación física del usuario en la base de datos.
            dao.eliminar(id);

// Se define mensaje de confirmación para mostrar en la vista.
            session.setAttribute("msg", "Usuario eliminado correctamente");
            session.setAttribute("msgType", "danger");

        } catch (Exception e) {
// Manejo de excepción general.
// Puede producirse por error de conversión o restricción de integridad.
            e.printStackTrace();

            session.setAttribute("msg", "Error al eliminar usuario");
            session.setAttribute("msgType", "danger");
        }
// Redirige al listado de usuarios.
// Se utiliza patrón PRG para evitar reenvío accidental.
        response.sendRedirect(request.getContextPath() + "/admin/usuarios.jsp");
    }
}