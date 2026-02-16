<%--
Archivo: editar_usuario.jsp
Módulo: Administración de Usuarios
Proyecto: TELEP_APP
Descripción:
    Vista encargada de mostrar el formulario de edición
    de un usuario existente. Carga los datos desde la base
    de datos y los precarga en el formulario para su actualización.
Tipo: Vista (JSP)
Autor: Julio Lopez
--%>

<%-- Importación de clases necesarias para consulta directa desde la vista --%>
<%@ page import="com.telep.dao.UsuarioDAO" %>
<%@ page import="com.telep.model.Usuario" %>
<%-- Inclusión del encabezado común del sistema --%>
<jsp:include page="/includes/header.jsp" />
<%--
    BLOQUE DE LÓGICA:
    Obtiene el ID enviado como parámetro,
    consulta la base de datos y recupera
    el usuario correspondiente.
--%>
<%
    int id = Integer.parseInt(request.getParameter("id"));
// Obtiene el identificador del usuario a editar
// enviado desde el listado administrativo.
    UsuarioDAO dao = new UsuarioDAO();
    Usuario u = null;
// Se recorre la lista de usuarios para encontrar
// el que coincide con el ID recibido.
    for (Usuario usr : dao.listarTodos()) {
        if (usr.getId() == id) {
            u = usr;
            break;
        }
    }
%>
<%-- Contenedor principal de la vista de edición --%>
<div class="container mt-4">
<div class="telep-card">

<h3 class="telep-title">Editar Usuario</h3>
<%--
    Formulario que envía los datos actualizados
    al servlet ActualizarUsuarioServlet.
--%>
<form method="post" action="<%=request.getContextPath()%>/admin/actualizar-usuario">
<%-- Campo oculto para mantener el ID del usuario --%>
<input type="hidden" name="id" value="<%=u.getId()%>">

<label>Nombre</label>
<input class="form-control mb-2" name="nombre" value="<%=u.getNombreCompleto()%>">

<label>Correo</label>
<input class="form-control mb-2" name="correo" value="<%=u.getCorreo()%>">
<%-- Selector de rol con selección dinámica según el rol actual --%>
<label>Rol</label>
<select name="rol" class="form-select mb-3">
    <option value="1" <%=u.getRolId()==1?"selected":""%>>Administrador</option>
    <option value="2" <%=u.getRolId()==2?"selected":""%>>Analista</option>
</select>
<%-- Botón para enviar cambios al servidor --%>
<button class="btn btn-dark">Guardar Cambios</button>
<%-- Enlace para regresar al listado sin guardar cambios --%>
<a href="usuarios.jsp" class="btn btn-secondary">Cancelar</a>

</form>

</div>
</div>
<%-- Inclusión del pie de página común del sistema --%>
<jsp:include page="/includes/footer.jsp" />