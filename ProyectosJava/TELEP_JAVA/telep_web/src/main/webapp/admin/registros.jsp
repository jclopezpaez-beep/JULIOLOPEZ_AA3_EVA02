<%--
Archivo: registro.jsp
Módulo: Panel Analista
Proyecto: TELEP_APP
Descripción:
    Vista que permite al usuario con rol ANALISTA
    registrar información técnica de equipos asignados
    a clientes. Incluye validación de sesión y rol.
Tipo: Vista (JSP)
Autor: Julio Lopez
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.telep.model.Usuario" %>
<%--
    BLOQUE DE SEGURIDAD:
    Verifica que exista sesión activa y que el usuario
    tenga rol de ANALISTA (rolId = 2).
    En caso contrario redirige al login.
--%>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    Integer rolId = (Integer) session.getAttribute("rolId");

// Validación estricta de acceso exclusivo para Analistas.
    if (usuario == null || rolId == null || rolId != 2) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro Técnico</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/telep.css">
</head>

<body>
<%-- Inclusión del encabezado global del sistema --%>
<jsp:include page="/includes/header.jsp" />
<%-- Contenedor principal del formulario de registro técnico --%>
<div class="container mt-4">

    <div class="telep-card">

        <h3 class="telep-title mb-3">Panel de Analista – Registro Técnico</h3>

<%-- Información del usuario autenticado actualmente --%>
        <div class="alert alert-secondary">
            <strong>Analista:</strong> <%= usuario.getUsuario() %>
        </div>
<%--
    Formulario que envía los datos al
    RegistroAnalistaServlet para persistencia en BD.
--%>
        <form action="<%= request.getContextPath() %>/registroAnalista" method="post">

<%-- ================= SECCIÓN: DATOS DEL CLIENTE =================
     Información básica del cliente y serial del equipo.
--%>
            <div class="card mb-4">
                <div class="card-header bg-dark text-white">
                    Datos del Cliente
                </div>

                <div class="card-body row g-3">

                    <div class="col-md-6">
                        <label class="form-label">Cliente</label>
                        <select name="cliente" class="form-select" required>
                            <option value="">-- Seleccione una opción --</option>
                            <option>Nexus Prime Solutions</option>
                            <option>AlphaStream Digital</option>
                            <option>Marea Grafica Estudio</option>
                            <option>Vertice Capital Group</option>
                            <option>Ruta 0 Express</option>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Serial del Equipo</label>
                        <input type="text"
                               name="serial"
                               class="form-control"
                               required>
                    </div>

                </div>
            </div>

<%-- ================= SECCIÓN: DATOS DEL EQUIPO =================
     Especificaciones técnicas del dispositivo registrado.
--%>
            <div class="card mb-4">
                <div class="card-header bg-secondary text-white">
                    Datos del Equipo
                </div>

                <div class="card-body row g-3">

                    <div class="col-md-4">
                        <label class="form-label">Tipo de Equipo</label>
                        <select name="equipo" class="form-select" required>
                            <option value="">-- Seleccione --</option>
                            <option>Desktop</option>
                            <option>Laptop</option>
                            <option>Especial</option>
                        </select>
                    </div>

                    <div class="col-md-4">
                        <label class="form-label">Modelo</label>
                        <select name="modelo" class="form-select" required>
                            <option value="">-- Seleccione --</option>
                            <option>ThinkPad</option>
                            <option>Yoga</option>
                            <option>Legion</option>
                            <option>IdeaPad</option>
                            <option>ThinkClient</option>
                            <option>Estándar</option>
                            <option>M729Q</option>
                        </select>
                    </div>

                    <div class="col-md-4">
                        <label class="form-label">RAM</label>
                        <select name="ram" class="form-select" required>
                            <option value="">-- Seleccione --</option>
                            <option>8 GB</option>
                            <option>12 GB</option>
                            <option>16 GB</option>
                            <option>24 GB</option>
                            <option>32 GB</option>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Almacenamiento</label>
                        <select name="almacenamiento" class="form-select" required>
                            <option value="">-- Seleccione --</option>
                            <option>SSD 250 GB</option>
                            <option>SSD 500 GB</option>
                            <option>SSD 1 TB</option>
                            <option>SSD 2 TB</option>
                            <option>HDD 250 GB</option>
                            <option>HDD 500 GB</option>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Observaciones</label>
                        <textarea name="observaciones"
                                  class="form-control"
                                  rows="3"
                                  required></textarea>
                    </div>

                </div>
            </div>

<%-- ================= ACCIONES DEL FORMULARIO =================
     Botón volver y botón de envío del registro.
--%>
            <div class="d-flex justify-content-between">
                <a href="<%= request.getContextPath() %>/dashboard.jsp"
                   class="btn btn-secondary">
                    ← Volver
                </a>

                <button type="submit" class="btn btn-success px-4">
                    Registrar Información
                </button>
            </div>

        </form>

    </div>
</div>
<%-- Inclusión del pie de página global del sistema --%>
<jsp:include page="/includes/footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>