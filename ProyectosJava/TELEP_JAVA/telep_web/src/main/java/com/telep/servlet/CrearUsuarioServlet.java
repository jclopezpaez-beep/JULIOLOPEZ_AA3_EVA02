/**
 * Archivo: CrearUsuarioServlet.java
 * Paquete: com.telep.servlet
 * Proyecto: TELEP_APP
 * Descripción: Servlet encargado de registrar nuevos usuarios
 *              desde el módulo administrativo.
 *              Implementa hash de contraseña antes de persistir
 *              los datos en la base de datos.
 * Tipo: Controlador (Servlet)
 * Autor: Julio Lopez
 */

package com.telep.servlet;

import com.telep.dao.UsuarioDAO;
import com.telep.model.Usuario;
import com.telep.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
// Mapea este servlet a la ruta protegida para creación de usuarios.
// Solo accesible para el rol Administrador.
@WebServlet("/admin/crear-usuario")
// Controlador encargado de procesar el formulario
// de registro de nuevos usuarios del sistema.
public class CrearUsuarioServlet extends HttpServlet {
// Procesa la solicitud POST enviada desde el formulario
// de creación de usuario en el panel administrativo.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
// Obtiene la sesión actual para almacenar mensajes
// de resultado que serán mostrados en la vista.
        HttpSession session = request.getSession();
// Se encapsula la operación en un bloque try-catch
// para manejar posibles errores de conversión o persistencia.
        try {
// Se obtienen los datos enviados desde el formulario:
// usuario, nombre, correo, contraseña y rol.
            String usuario = request.getParameter("usuario");
            String nombre = request.getParameter("nombre");
            String correo = request.getParameter("correo");
            String password = request.getParameter("password");
            int rolId = Integer.parseInt(request.getParameter("rol"));
// Se construye el objeto Usuario con los datos recibidos.
            Usuario u = new Usuario();
            u.setUsuario(usuario);
            u.setNombreCompleto(nombre);
            u.setCorreo(correo);

// HASH DE CONTRASEÑA
// ============================
// SEGURIDAD: HASH DE CONTRASEÑA
// La contraseña en texto plano nunca se almacena.
// Se genera un hash seguro mediante PasswordUtil.
// ============================
            String hash = PasswordUtil.hashPassword(password);
            u.setPasswordHash(hash);

            u.setRolId(rolId);
            u.setEstado("activo");
// Se instancia el DAO para delegar la inserción
// del nuevo usuario en la base de datos.
            UsuarioDAO dao = new UsuarioDAO();
// Se ejecuta la inserción del usuario.
// Si retorna true, el registro fue exitoso.
            if (dao.insertar(u)) {
// Se define mensaje de éxito para mostrar en la vista.
                session.setAttribute("msg", "Usuario Creado Correctamente");
                session.setAttribute("msgType", "success");
            }else {
// Se define mensaje de error si la inserción falló.
                session.setAttribute("msg","No Se Pudo Crear el Usurio");
                session.setAttribute("msgType","danger");
            }
            
        } catch (Exception e) {
// Manejo de excepción general.
// Puede producirse por error de base de datos o conversión.            
            e.printStackTrace();
                session.setAttribute("msg","Error Inespereado al Crear el Usuario");
                session.setAttribute("msgType", "danger");
        }
// Redirige al listado de usuarios.
// Se aplica el patrón PRG (Post/Redirect/Get)
// para evitar reenvío del formulario.
        response.sendRedirect(request.getContextPath() + "/admin/usuarios.jsp");
    }
}
