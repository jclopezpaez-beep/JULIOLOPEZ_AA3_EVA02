package com.telep.test;

import com.telep.config.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/test-db")
public class TestConnectionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        try (PrintWriter out = response.getWriter()) {

            try (Connection conn = DBConnection.getConnection()) {

                if (conn != null && !conn.isClosed()) {
                    out.println("✔ Conexión EXITOSA a la base de datos MySQL.");
                } else {
                    out.println("✖ No se pudo establecer la conexión.");
                }

            } catch (SQLException e) {
                out.println("✖ Error SQL al conectar: " + e.getMessage());
            }
        }
    }
}
