/**
 * Archivo: UsuarioDAO.java
 * Paquete: com.telep.dao
 * Proyecto: TELEP_APP
 * Descripción: Clase DAO encargada de gestionar las operaciones
 *              de acceso a datos relacionadas con la entidad Usuario.
 *              Incluye registro, autenticación, consulta, actualización
 *              y eliminación de usuarios del sistema.
 * Patrón aplicado: Data Access Object (DAO)
 * Autor: [Tu nombre]
 */

package com.telep.dao;

import com.telep.config.DBConnection;
import com.telep.model.Usuario;
import com.telep.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// Clase que encapsula todas las operaciones CRUD
// y procesos de autenticación relacionados con la tabla usuarios.

public class UsuarioDAO {

// INSERTAR
// ============================
// MÉTODO: insertar()
// Descripción: Inserta un nuevo usuario en la base de datos.
// Recibe un objeto Usuario previamente validado.
// Retorna true si la inserción fue exitosa.
// ============================
    public boolean insertar(Usuario u) {
// Consulta SQL parametrizada para registrar un nuevo usuario.
// La contraseña ya debe estar en formato hash antes de insertarse.
        String sql = "INSERT INTO usuarios (usuario, nombre_completo, correo, password_hash, rol_id, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
// Se utiliza try-with-resources para asegurar el cierre automático
// de la conexión y el PreparedStatement.
        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, u.getUsuario());
            ps.setString(2, u.getNombreCompleto());
            ps.setString(3, u.getCorreo());
            ps.setString(4, u.getPasswordHash());
            ps.setInt(5, u.getRolId());
            ps.setString(6, u.getEstado());
            ps.executeUpdate();
            return true;
// Ejecuta la inserción del nuevo usuario en la base de datos.
        } catch (Exception e) {
// En caso de error se imprime la traza para diagnóstico.
            e.printStackTrace();
            return false;
        }
    }

// LOGIN
// ============================
// MÉTODO: login()
// Descripción: Valida las credenciales de acceso del usuario.
// Verifica que el usuario exista, esté activo y que la contraseña
// coincida con el hash almacenado en base de datos.
// Retorna un objeto Usuario si la autenticación es correcta,
// o null en caso contrario.
// ============================
    public Usuario login(String usuario, String passwordPlano) {
// Consulta SQL que obtiene el usuario solo si está en estado activo.
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND estado = 'activo'";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
// Verifica si existe un usuario con el nombre proporcionado.
            if (rs.next()) {
                String hashBD = rs.getString("password_hash");
// Se compara la contraseña en texto plano con el hash almacenado
// utilizando la utilidad PasswordUtil para validación segura.
                if (PasswordUtil.checkPassword(passwordPlano, hashBD)) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setUsuario(rs.getString("usuario"));
                    u.setNombreCompleto(rs.getString("nombre_completo"));
                    u.setCorreo(rs.getString("correo"));
                    u.setRolId(rs.getInt("rol_id"));
                    u.setEstado(rs.getString("estado"));
                    return u;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
 // Si las credenciales no coinciden o el usuario no existe,
// se retorna null indicando autenticación fallida.
        return null;
    }

// LISTAR COMPLETO
// ============================
// MÉTODO: listarTodos()
// Descripción: Obtiene la lista completa de usuarios registrados.
// Incluye una conversión del rol_id a su nombre descriptivo.
// ============================
    public List<Usuario> listarTodos() {
// Lista que almacenará todos los usuarios obtenidos.
        List<Usuario> lista = new ArrayList<>();
// Consulta SQL que obtiene todos los usuarios
// e interpreta el rol_id en un valor descriptivo mediante CASE.       
        String sql = """
            SELECT 
                u.id,
                u.usuario,
                u.nombre_completo,
                u.correo,
                u.rol_id,
                CASE 
                    WHEN u.rol_id = 1 THEN 'Administrador'
                    WHEN u.rol_id = 2 THEN 'Analista'
                    ELSE 'Desconocido'
                END AS rol,
                u.estado
            FROM usuarios u
            ORDER BY u.id
        """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
// Se recorren los resultados y se construyen
// objetos Usuario por cada registro encontrado.
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setUsuario(rs.getString("usuario"));
                u.setNombreCompleto(rs.getString("nombre_completo"));
                u.setCorreo(rs.getString("correo"));
                u.setRolId(rs.getInt("rol_id"));
                u.setRol(rs.getString("rol"));
                u.setEstado(rs.getString("estado"));
// Se agrega el usuario a la lista final.
                lista.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

// CAMBIAR ESTADO
// ============================
// MÉTODO: cambiarEstado()
// Descripción: Actualiza el estado (activo/inactivo)
// de un usuario específico.
// ============================
    public void cambiarEstado(int id, String estado) {
// Consulta SQL para actualizar el estado del usuario.
        String sql = "UPDATE usuarios SET estado = ? WHERE id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, estado);
            ps.setInt(2, id);
// Ejecuta la actualización del estado en la base de datos.
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// ACTUALIZAR
// ============================
// MÉTODO: actualizar()
// Descripción: Actualiza los datos básicos de un usuario
// (nombre, correo y rol).
// Retorna true si la actualización fue exitosa.
// ============================

public boolean actualizar(Usuario u) {
// Consulta SQL para modificar la información editable del usuario.
    String sql = "UPDATE usuarios SET nombre_completo=?, correo=?, rol_id=? WHERE id=?";

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setString(1, u.getNombreCompleto());
        ps.setString(2, u.getCorreo());
        ps.setInt(3, u.getRolId());
        ps.setInt(4, u.getId());
// Ejecuta la actualización y retorna true si se afectó al menos una fila.
        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

// ELIMINAR
// ============================
// MÉTODO: eliminar()
// Descripción: Elimina físicamente un usuario de la base de datos.
// ============================
    public void eliminar(int id) {
// Consulta SQL para eliminar el usuario según su identificador.
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
// Ejecuta la eliminación del registro en la base de datos.
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
