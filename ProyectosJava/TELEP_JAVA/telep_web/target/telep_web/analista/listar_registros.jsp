<%--Archivo: listar_registros.jsp
Módulo: Panel Analista
Proyecto: TELEP_APP
Descripción:
    Vista que muestra los registros técnicos creados
    por el analista autenticado. Solo accesible para
    usuarios con rol ANALISTA (rolId = 2).
Tipo: Vista (JSP)
Autor: Julio Lopez
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.telep.model.Usuario" %>
<%@ page import="com.telep.model.RegistroAnalista" %>
<%@ page import="com.telep.dao.RegistroAnalistaDAO" %>

<%--
    BLOQUE DE SEGURIDAD:
    Verifica que exista sesión activa y que el usuario
    tenga rol ANALISTA.
    Si no cumple, redirige al login.
--%>

<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    Integer rolId = (Integer) session.getAttribute("rolId");

// Validación estricta de acceso exclusivo para analistas.
    if (usuario == null || rolId == null || rolId != 2) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
// Instancia del DAO para consultar registros
// asociados al usuario autenticado.
    RegistroAnalistaDAO dao = new RegistroAnalistaDAO();
// Consulta todos los registros creados por el analista actual.
    List<RegistroAnalista> registros = dao.listarPorUsuario(usuario.getId());
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mis Registros</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/telep.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<%-- Inclusión del encabezado global del sistema --%>
<jsp:include page="/includes/header.jsp" />

<div class="container mt-4">
<%-- Título principal de la vista --%>
    <h2 class="mb-3">Mis Registros Técnicos</h2>
<%-- Información del analista autenticado --%>
    <p>
        Analista:
        <strong><%= usuario.getUsuario() %></strong>
    </p>

    <hr>
<%--
    Si no existen registros, se muestra
    mensaje informativo al usuario.
--%>
    <% if (registros.isEmpty()) { %>
        <div class="alert alert-info">
            No tienes registros creados aún.
        </div>
    <% } else { %>
<%-- Tabla que muestra los registros técnicos creados --%>
        <div class="table-responsive">
            <table class="table table-striped table-bordered align-middle">
                <thead class="table-dark text-center">
                    <tr>
                        <th>ID</th>
                        <th>Cliente</th>
                        <th>Equipo</th>
                        <th>Modelo</th>
                        <th>RAM</th>
                        <th>Almacenamiento</th>
                        <th>Serial</th>
                        <th>Estado</th>
                        <th>Fecha</th>
                    </tr>
                </thead>
                <tbody>
<%-- Iteración dinámica de cada registro del analista --%>
                <% for (RegistroAnalista r : registros) { %>
                    <tr>
                        <td class="text-center"><%= r.getId() %></td>
                        <td><%= r.getCliente() %></td>
                        <td><%= r.getEquipo() %></td>
                        <td><%= r.getModelo() %></td>
                        <td><%= r.getRam() %></td>
                        <td><%= r.getAlmacenamiento() %></td>
                        <td><%= r.getSerial() %></td>
                        <td class="text-center">
                            <span class="badge bg-secondary">
                                <%= r.getEstadoRevision() %>
                            </span>
                        </td>
                        <td class="text-center">
                            <%= r.getFechaRegistro() %>
                        </td>
                    </tr>
                <% } %>

                </tbody>
            </table>
        </div>

    <% } %>
<%-- Enlace para crear un nuevo registro técnico --%>
    <a href="<%= request.getContextPath() %>/analista/registro.jsp"
       class="btn btn-primary mt-3">
        + Registrar nueva información
    </a>
<%-- Regresar al panel principal del sistema --%>
    <a href="<%= request.getContextPath() %>/dashboard.jsp"
       class="btn btn-secondary mt-3 ms-2">
        ← Volver al Dashboard
    </a>

</div>
<%-- Inclusión del pie de página global --%>
<jsp:include page="/includes/footer.jsp" />
</body>
</html>