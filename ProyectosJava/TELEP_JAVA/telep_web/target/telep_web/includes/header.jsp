<%--
Archivo: header.jsp
Ubicación: /includes/
Proyecto: TELEP_APP
Descripción:
    Encabezado global reutilizable del sistema.
    Incluye:
    - Configuración HTML base
    - Carga de Bootstrap
    - Carga de Bootstrap Icons
    - Carga del CSS corporativo
    - Navbar principal del sistema
Tipo: Fragmento reutilizable (JSP Include)
Autor: Julio Lopez
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- Estructura base HTML del sistema --%>
<!DOCTYPE html>
<html>
<%-- Sección HEAD con dependencias globales --%>
<head>
    <title>TELEP</title>

<%-- Bootstrap 5 CSS (CDN oficial) --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<%-- Librería de iconos Bootstrap Icons --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

<%-- Hoja de estilos corporativa del sistema TELEP --%>
    <link href="<%= request.getContextPath() %>/css/telep.css" rel="stylesheet">
</head>

<body>

<%-- ================= NAVBAR PRINCIPAL DEL SISTEMA =================
     Barra de navegación superior reutilizable.
--%>
<nav class="navbar navbar-expand-lg telep-navbar">
<%-- Contenedor principal del navbar --%>
    <div class="container-fluid">

<%-- Logo institucional y enlace al dashboard --%>
        <a class="navbar-brand d-flex align-items-center" href="<%= request.getContextPath() %>/dashboard.jsp">
            <img src="<%= request.getContextPath() %>/images/logo.png"
                 alt="Logo TELEP"
                 class="telep-navbar-logo">
            <span class="ms-2 telep-navbar-title">Formulario</span>
        </a>

<%-- Botón hamburguesa para dispositivos móviles --%>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#telepNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>

<%-- Menú de navegación derecho (Inicio y Salir) --%>
        <div class="collapse navbar-collapse justify-content-end" id="telepNavbar">
            <ul class="navbar-nav mb-2 mb-lg-0">
<%-- Enlace al Dashboard principal --%>
                <li class="nav-item">
                    <a class="nav-link" href="<%= request.getContextPath() %>/dashboard.jsp">
                        <i class="bi bi-house-fill"></i> Inicio
                    </a>
                </li>
<%-- Enlace para cerrar sesión (LogoutServlet) --%>
                <li class="nav-item">
                    <a class="nav-link text-danger" href="<%= request.getContextPath() %>/logout">
                        <i class="bi bi-box-arrow-right"></i> Salir
                    </a>
                </li>

            </ul>
        </div>
    </div>
</nav>
