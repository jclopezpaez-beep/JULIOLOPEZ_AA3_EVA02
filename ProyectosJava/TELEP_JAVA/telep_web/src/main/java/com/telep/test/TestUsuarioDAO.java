package com.telep.test;

import com.telep.dao.UsuarioDAO;
import com.telep.model.Usuario;

public class TestUsuarioDAO {

    public static void main(String[] args) {

        UsuarioDAO dao = new UsuarioDAO();

        String usuario = "telep_java";
        String password = "Java1234"; // EXACTAMENTE el de la BD

        Usuario u = dao.login(usuario, password);

        if (u != null) {
            System.out.println("LOGIN EXITOSO");
            System.out.println("Usuario: " + u.getUsuario());
            System.out.println("Rol ID: " + u.getRolId());
        } else {
            System.out.println("LOGIN FALLIDO");
        }
    }
}