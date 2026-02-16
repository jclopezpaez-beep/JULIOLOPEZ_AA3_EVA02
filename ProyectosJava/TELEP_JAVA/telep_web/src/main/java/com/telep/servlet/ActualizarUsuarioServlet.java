/**
 * Archivo: ActualizarUsuarioServlet.java
 * Paquete: com.telep.servlet
 * Proyecto: TELEP_APP
 * Descripción: Servlet encargado de procesar la actualización
 *              de datos de un usuario desde el módulo administrativo.
 *              Recibe datos desde un formulario POST y delega la
 *              operación al UsuarioDAO.
 * Tipo: Controlador (Servlet)
 * Autor: Julio Lopez
 */
package com.telep.servlet;

import com.telep.dao.UsuarioDAO;
import com.telep.model.Usuario;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
// Mapea este servlet a la ruta protegida del módulo administrador.
// Solo accesible para usuarios con rol Administrador (filtrado por AdminFilter).
@WebServlet("/admin/actualizar-usuario")
// Controlador que gestiona la actualización de información
// básica de un usuario (nombre, correo y rol).
public class ActualizarUsuarioServlet extends HttpServlet {
// Procesa la solicitud POST enviada desde el formulario
// de edición de usuario en el panel administrativo.
    protected void doPost(HttpServletRequest req, HttpServletResponse response)
            throws IOException {
// Obtiene la sesión actual para almacenar mensajes
// de retroalimentación que serán mostrados en la vista.
        HttpSession session = req.getSession();
// Se encapsula la operación en un bloque try-catch
// para manejar posibles errores de conversión o base de datos.
        try {
// Se construye el objeto Usuario a partir de los parámetros
// recibidos desde el formulario.
            Usuario u = new Usuario();
// Se asignan los valores enviados en el formulario.
// Se realiza conversión a entero para id y rol.
            u.setId(Integer.parseInt(req.getParameter("id")));
            u.setNombreCompleto(req.getParameter("nombre"));
            u.setCorreo(req.getParameter("correo"));
            u.setRolId(Integer.parseInt(req.getParameter("rol")));
// Se crea instancia del DAO para delegar la actualización
// en la capa de acceso a datos.
            UsuarioDAO dao = new UsuarioDAO();
// Se ejecuta la actualización en base de datos.
// Si la operación retorna true, significa que se modificó al menos un registro.
            if (dao.actualizar(u)) {
// Se define mensaje de éxito para mostrar en la vista.
                session.setAttribute("msg", "Usuario actualizado correctamente");
                session.setAttribute("msgType", "success");

            } else {
// Se define mensaje de error si la actualización no tuvo efecto.
                session.setAttribute("msg", "No se pudo actualizar el usuario");
                session.setAttribute("msgType", "danger");
            }

        } catch (Exception e) {

            e.printStackTrace();
// Manejo de excepción general.
// Puede producirse por error de conversión o fallo en base de datos.
            session.setAttribute("msg", "Error al actualizar usuario");
            session.setAttribute("msgType", "danger");
        }
// Redirige nuevamente al listado de usuarios.
// La vista debe interpretar los atributos "msg" y "msgType".
        response.sendRedirect(req.getContextPath()+"/admin/usuarios.jsp");
    }
}