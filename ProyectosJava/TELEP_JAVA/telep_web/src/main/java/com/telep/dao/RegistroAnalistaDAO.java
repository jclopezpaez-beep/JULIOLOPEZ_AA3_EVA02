/**
 * Archivo: RegistroAnalistaDAO.java
 * Paquete: com.telep.dao
 * Proyecto: TELEP_APP
 * Descripción: Clase DAO encargada de gestionar las operaciones de acceso
 *              a datos relacionadas con la entidad RegistroAnalista.
 *              Permite insertar registros y consultar registros por usuario.
 * Patrón aplicado: Data Access Object (DAO)
 * Autor: Julio Lopez
 */

package com.telep.dao;

import com.telep.config.DBConnection;
import com.telep.model.RegistroAnalista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// Clase responsable de interactuar directamente con la tabla
// registros_analistas en la base de datos.
// Centraliza las operaciones CRUD relacionadas con esta entidad.

public class RegistroAnalistaDAO {

// INSERTAR REGISTRO
// ============================
// MÉTODO: insertar()
// Descripción: Inserta un nuevo registro de configuración realizado por un analista
// en la tabla registros_analistas.
// Retorna true si la operación fue exitosa, false en caso de error.
// ============================

    public boolean insertar(RegistroAnalista r) {

// Consulta SQL parametrizada para insertar un nuevo registro.
// Se utilizan parámetros (?) para prevenir SQL Injection.

        String sql = """
            INSERT INTO registros_analistas
            (id_usuario, cliente, campo_equipo, campo_modelo,
             campo_ram, campo_almacenamiento, campo_serial, campo_observaciones)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
// Se utiliza try-with-resources para garantizar el cierre automático
// de la conexión y el PreparedStatement.

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
 // Asignación de valores a los parámetros de la consulta
// utilizando los datos del objeto RegistroAnalista.           
            ps.setInt(1, r.getIdUsuario());
            ps.setString(2, r.getCliente());
            ps.setString(3, r.getEquipo());
            ps.setString(4, r.getModelo());
            ps.setString(5, r.getRam());
            ps.setString(6, r.getAlmacenamiento());
            ps.setString(7, r.getSerial());
            ps.setString(8, r.getObservaciones());

// Ejecuta la operación de inserción en la base de datos.            
            ps.executeUpdate();
            return true;
// En caso de error se imprime la traza para diagnóstico.
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // LISTAR REGISTROS POR USUARIO

// ============================
// MÉTODO: listarPorUsuario()
// Descripción: Obtiene todos los registros asociados a un usuario específico.
// Retorna una lista ordenada por fecha de registro descendente.
// ============================
    
    public List<RegistroAnalista> listarPorUsuario(int idUsuario) {
// Lista que almacenará los registros obtenidos desde la base de datos.
        List<RegistroAnalista> lista = new ArrayList<>();
// Consulta SQL que obtiene los registros filtrados por id_usuario
// y los ordena por fecha de registro descendente.
        String sql = """
            SELECT 
                id,
                id_usuario,
                cliente,
                campo_equipo,
                campo_modelo,
                campo_ram,
                campo_almacenamiento,
                campo_serial,
                campo_estado_revision,
                fecha_registro
            FROM registros_analistas
            WHERE id_usuario = ?
            ORDER BY fecha_registro DESC
        """;
// Se establece la conexión y se prepara la consulta utilizando
// try-with-resources para cerrar recursos automáticamente.
        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
// Se asigna el identificador del usuario como parámetro de filtrado.
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
// Se recorren los resultados obtenidos y se construyen
// objetos RegistroAnalista por cada fila encontrada.
            while (rs.next()) {
                RegistroAnalista r = new RegistroAnalista();

                r.setId(rs.getInt("id"));
                r.setIdUsuario(rs.getInt("id_usuario"));
                r.setCliente(rs.getString("cliente"));
                r.setEquipo(rs.getString("campo_equipo"));
                r.setModelo(rs.getString("campo_modelo"));
                r.setRam(rs.getString("campo_ram"));
                r.setAlmacenamiento(rs.getString("campo_almacenamiento"));
                r.setSerial(rs.getString("campo_serial"));
                r.setEstadoRevision(rs.getString("campo_estado_revision"));
               
                r.setFechaRegistro(
                    rs.getTimestamp("fecha_registro").toLocalDateTime()
                );
// Se agrega el objeto mapeado a la lista de resultados.
                lista.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
// Retorna la lista de registros asociados al usuario.
        return lista;
    }
}