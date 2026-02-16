<%--
Archivo: dashboard.jsp
Ubicación: /
Proyecto: TELEP_APP
Descripción:
    Página principal del sistema.
    Muestra el panel dinámico según:
    - Rol del usuario
    - Estado del modoAnalista
    Valida sesión activa antes de mostrar contenido.
Tipo: Vista principal protegida
Autor: Julio Lopez
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.telep.model.Usuario" %>
<%-- ================= VALIDACIÓN DE SESIÓN =================
     Se obtiene:
     - Usuario autenticado
     - Rol del usuario
     - Estado del modoAnalista
--%>
<%

    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    Integer rolId = (Integer) session.getAttribute("rolId");
    Boolean modoAnalista = (Boolean) session.getAttribute("modoAnalista");

    if (modoAnalista == null) {
        modoAnalista = false;
    }

//Si no hay sesión válida, redirige al login

    if (usuario == null || rolId == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<%-- ================= ESTRUCTURA HTML ================= --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard - TELEP</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/telep.css">
</head>

<body>
<%-- Header global del sistema --%>
<jsp:include page="/includes/header.jsp" />

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">

<%-- ================= TARJETA PRINCIPAL DEL DASHBOARD ================= --%>
            <div class="telep-card">
<%-- Información básica del usuario autenticado --%>
                <h2 class="telep-h1">Bienvenido al sistema</h2>

                <p class="telep-text"><strong>Usuario:</strong> <%= usuario.getUsuario() %></p>
                <p class="telep-text"><strong>Rol:</strong>
                    <%= (rolId == 1 ? "Administrador" : "Analista") %>
                </p>

                <hr>

<%-- ================= PANEL ADMINISTRADOR (MODO NORMAL) =================
     Rol 1 sin modoAnalista activado
--%>
                <% if (rolId == 1 && !modoAnalista) { %>

                    <h3 class="telep-h2">Panel de Administrador</h3>

                    <ul class="telep-menu">
                        <li class="list-group-item">
                            <a href="<%= request.getContextPath() %>/admin/usuarios.jsp">
                                <i class="bi bi-people-fill"></i>
                                Gestionar Usuarios
                            </a>
                        </li>
                        <li class="list-group-item">
                            <a href="<%= request.getContextPath() %>/admin/registros.jsp">
                            <i class="bi bi-clipboard-data"></i>
                                Ver Registro Completo
                            </a>
                        </li>
                    </ul>

                    <form action="<%= request.getContextPath() %>/activar-modo-analista" method="get">
                        <button type="submit" class="btn btn-telep w-100">
                            <i class="bi bi-arrow-repeat"></i>
                            Ingresar como Analista
                        </button>
                    </form>

<%-- ================= ADMINISTRADOR EN MODO ANALISTA =================
     Permite que el administrador use funciones de analista
--%>
                <% } else if (rolId == 1 && modoAnalista) { %>

                    <h3 class="telep-h2">Panel de Analista (Modo Administrador)</h3>

                    <ul class="telep-menu">
                        <li class="list-group-item">
                            <a href="<%= request.getContextPath() %>/analista/registro.jsp">
                                <i class="bi bi-pencil-square"></i>
                                Registrar Información
                            </a>
                        </li>
                        <li class="list-group-item">
                            <a href="<%= request.getContextPath() %>/analista/misRegistros.jsp">
                                <i class="bi bi-folder-check"></i>
                                Ver Mis Registros
                            </a>
                        </li>
                    </ul>

                    <form action="<%= request.getContextPath() %>/desactivarModoAnalista" method="get">
                        <button type="submit" class="btn btn-telep w-100">
                            <i class="bi bi-arrow-left-circle"></i>
                            Volver a modo Administrador
                        </button>
                    </form>

<%-- ================= PANEL ANALISTA =================
     Usuario con rol 2
--%>
                <% } else if (rolId == 2) { %>

                    <h3 class="telep-h2">Panel de Analista</h3>

                    <ul class="telep-menu">
                        <li class="list-group-item">
                            <a href="<%= request.getContextPath() %>/analista/registro.jsp">
                                Registrar Información
                            </a>
                        </li>
                        <li class="list-group-item">
                            <a href="<%= request.getContextPath() %>/analista/listar_registros.jsp">
                                Ver Mis Registros
                            </a>
                        </li>
                    </ul>

                <% } %>

                <hr>
<%-- Botón global de cierre de sesión --%>
                <a href="<%= request.getContextPath() %>/logout"
                   class="btn btn-telep w-100">
                        <i class="bi bi-box-arrow-right"></i>
                        Cerrar sesión
                </a>

            </div>
        </div>
    </div>
</div>
<%-- Footer global reutilizable --%>
<jsp:include page="/includes/footer.jsp" />

</body>
</html>