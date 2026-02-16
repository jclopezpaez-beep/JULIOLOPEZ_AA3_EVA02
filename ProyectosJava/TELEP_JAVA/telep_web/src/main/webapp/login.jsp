<%--
Archivo: login.jsp
Ubicación: /
Proyecto: TELEP_APP
Descripción:
    Página pública de autenticación.
    Permite el ingreso de usuarios registrados.
    Envía credenciales al LoginServlet mediante método POST.
    Muestra mensaje de error si la autenticación falla.
Tipo: Vista pública
Autor: Julio Lopez
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- ================= ESTRUCTURA HTML LOGIN ================= --%>
<!DOCTYPE html>
<html>
<%-- Configuración HEAD y dependencias visuales --%>
<head>
    <meta charset="UTF-8">
    <title>Login - TELEP</title>

<%-- Bootstrap 5 CSS (CDN oficial) --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<%-- Estilos específicos del login (no globales) --%>
    <style>
    /* ================= ESTILOS DEL LOGIN ================= */
        body {
            background-color: #d3d3d3;
            height: 100vh;
            margin: 0;
        }

        .login-card {
            background-color: #9b9b9b;
            border-radius: 12px;
            padding: 25px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.2);
        }


    </style>
</head>

<body>
<%-- Header global reutilizable --%>
<jsp:include page="/includes/header.jsp" />

<%-- ================= CONTENEDOR CENTRAL DEL LOGIN =================
     Centrado vertical y horizontal con Bootstrap
--%>
<div class="container-fluid d-flex justify-content-center align-items-center h-100">
    <div class="col-11 col-sm-8 col-md-5 col-lg-4">

        <div class="login-card">
            <h3 class="text-center mb-4">Iniciar Sesión</h3>
<%-- Formulario de autenticación
     Envía datos al servlet /login usando método POST
--%>
            <form action="<%= request.getContextPath() %>/login" method="post">

                <div class="mb-3">
                    <label class="telep-label">Usuario</label>
                    <input type="text" name="usuario" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="telep-label">Contraseña</label>
                    <input type="password" name="password" class="form-control" required>
                </div>

                <button type="submit" class="btn btn-dark w-100">Ingresar</button>
<%-- Muestra mensaje si el LoginServlet redirige con parámetro error --%>
                <% if (request.getParameter("error") != null) { %>
                    <div class="alert alert-danger mt-3 text-center">
                        Usuario o contraseña incorrectos
                    </div>
                <% } %>

            </form>
        </div>

    </div>
</div>
<%-- Footer global del sistema --%>
<jsp:include page="/includes/footer.jsp" />
</body>
</html>