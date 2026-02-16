<%--
Archivo: footer.jsp
Ubicación: /includes/
Proyecto: TELEP_APP
Descripción:
    Pie de página global del sistema.
    Incluye:
    - Footer institucional
    - Sistema de notificaciones Toast (Bootstrap)
    - Carga de Bootstrap JS
Tipo: Fragmento reutilizable (JSP Include)
Autor: [Tu nombre]
Fecha: [Fecha]
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- Línea divisoria visual del footer --%>
<hr>
<%-- Información institucional del sistema --%>
<footer class="text-center mt-4 mb-3">
    <small>© 2026 TELEP - Sistema Interno</small>
</footer>

<%-- ================= SISTEMA DE NOTIFICACIONES =================
     Contenedor de mensajes dinámicos enviados
     desde los Servlets mediante sesión.
--%>
<div class="toast-container position-fixed top-0 end-0 p-3">
<%--
    Recupera mensaje y tipo (success, danger, etc.)
    almacenados previamente por un Servlet.
--%>
<%
    String msg = (String) session.getAttribute("msg");
    String type = (String) session.getAttribute("msgType");
// Si existe mensaje en sesión, se construye el Toast dinámicamente.
    if (msg != null) {
        if (type == null) type = "success";
%>
<%-- Toast Bootstrap dinámico con color según tipo recibido --%>
    <div id="liveToast"
         class="toast align-items-center text-bg-<%=type%> border-0"
         role="alert">

        <div class="d-flex">
            <div class="toast-body">
                <%=msg%>
            </div>
            <button type="button"
                    class="btn-close btn-close-white me-2 m-auto"
                    data-bs-dismiss="toast">
            </button>
        </div>
    </div>

<%
// Limpia los atributos de sesión para evitar duplicación del mensaje.
        session.removeAttribute("msg");
        session.removeAttribute("msgType");
    }
%>

</div>

<%-- Carga del bundle de Bootstrap (incluye JS y Popper) --%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<%--
    Si existió mensaje, se activa el Toast
    automáticamente al cargar la página.
--%>
<% if (msg != null) { %>
<script>
// Inicializa y muestra el Toast automáticamente
    const toast = new bootstrap.Toast(document.getElementById('liveToast'));
    toast.show();
</script>
<% } %>