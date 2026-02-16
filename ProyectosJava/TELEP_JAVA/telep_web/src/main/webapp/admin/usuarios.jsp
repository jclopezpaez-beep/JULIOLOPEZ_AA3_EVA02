<%--
Archivo: usuarios.jsp
Módulo: Administración
Proyecto: TELEP_APP
Descripción:
    Vista principal para la gestión de usuarios.
    Permite crear, listar, activar/desactivar,
    editar y eliminar usuarios del sistema.
Tipo: Vista (JSP)
Autor: Julio Lopez
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.telep.dao.UsuarioDAO" %>
<%@ page import="com.telep.model.Usuario" %>
<%-- Inclusión del encabezado global del sistema --%>
<jsp:include page="/includes/header.jsp" />

<div class="container mt-4">
    <div class="telep-card">

        <h2 class="telep-title">Gestión de Usuarios</h2>
<%-- Muestra el usuario administrador actualmente autenticado --%>
        <p>
            Bienvenido administrador:
            <strong><%= ((Usuario) session.getAttribute("usuarioLogueado")).getUsuario() %></strong>
        </p>

        <hr>
<%-- Campo de búsqueda dinámica para filtrar usuarios en la tabla --%>
        <input type="text" id="buscarUsuario"
               class="form-control mb-3"
               placeholder="🔍 Buscar usuario...">

        <h4 class="mb-3">Crear Nuevo Usuario</h4>
<%--
    Formulario para crear un nuevo usuario.
    Envía datos al CrearUsuarioServlet.
--%>
        <form method="post" action="<%=request.getContextPath()%>/admin/crear-usuario">
            <div class="row g-2">
                <div class="col-md-3">
                    <input type="text" name="usuario" class="form-control" placeholder="Usuario de red" required>
                </div>
                <div class="col-md-3">
                    <input type="text" name="nombre" class="form-control" placeholder="Nombre completo" required>
                </div>
                <div class="col-md-3">
                    <input type="email" name="correo" class="form-control" placeholder="Correo">
                </div>
                <div class="col-md-2">
                    <input type="password" name="password" class="form-control" placeholder="Contraseña" required>
                </div>
                <div class="col-md-2">
                    <select name="rol" class="form-select">
                        <option value="2">Analista</option>
                        <option value="1">Administrador</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <button class="btn btn-dark w-100">Crear Usuario</button>
                </div>
            </div>
        </form>

        <hr>
<%--
    BLOQUE DE CONSULTA:
    Obtiene la lista completa de usuarios
    desde la base de datos.
--%>
        <%
            UsuarioDAO dao = new UsuarioDAO();
            List<Usuario> usuarios = dao.listarTodos();
        %>
<%-- Tabla que muestra todos los usuarios registrados --%>
        <div class="table-responsive">
            <table class="table table-striped table-bordered align-middle">
                <thead class="table-dark text-center">
                    <tr>
                        <th>ID</th>
                        <th>Usuario</th>
                        <th>Rol</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
<%-- Iteración de cada usuario para generar filas dinámicas --%>
                <% for (Usuario u : usuarios) { %>
                    <tr>
                        <td class="text-center"><%= u.getId() %></td>
                        <td><%= u.getUsuario() %></td>
                        <td class="text-center">
                            <%= (u.getRolId() == 1 ? "Administrador" : "Analista") %>
                        </td>
                        <td class="text-center">
<%-- Badge visual que indica si el usuario está activo o inactivo --%>
                            <span class="badge <%= u.getEstado().equals("activo") ? "bg-success" : "bg-secondary" %>">
                                <%= u.getEstado() %>
                            </span>
                        </td>
                        <td class="text-center d-flex justify-content-center gap-1">
<%-- Botón dinámico según estado actual del usuario --%>
                            <% if ("activo".equals(u.getEstado())) { %>
                                <a class="btn btn-sm btn-warning"
                                   href="<%=request.getContextPath()%>/admin/cambiar-estado-usuario?id=<%=u.getId()%>&estado=inactivo">
                                    Desactivar
                                </a>
                            <% } else { %>
                                <a class="btn btn-sm btn-success"
                                   href="<%=request.getContextPath()%>/admin/cambiar-estado-usuario?id=<%=u.getId()%>&estado=activo">
                                    Activar
                                </a>
                            <% } %>
<%-- Redirige a la vista de edición del usuario --%>
                            <a class="btn btn-sm btn-primary"
                               href="<%=request.getContextPath()%>/admin/editarUsuario.jsp?id=<%=u.getId()%>">
                                Editar
                            </a>

<%-- Botón que abre modal de confirmación para eliminar usuario --%>
                            <button class="btn btn-sm btn-danger"
                                    data-bs-toggle="modal"
                                    data-bs-target="#modalEliminar"
                                    data-id="<%=u.getId()%>">
                                Eliminar
                            </button>

                        </td>
                    </tr>
                <% } %>

                </tbody>
            </table>
        </div>

        <a href="<%= request.getContextPath() %>/dashboard.jsp"
           class="btn btn-secondary mt-3">
            ← Volver al Dashboard
        </a>

    </div>
</div>

<%-- Modal Bootstrap para confirmar eliminación de usuario --%>
<div class="modal fade" id="modalEliminar" tabindex="-1">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">

      <div class="modal-header bg-danger text-white">
        <h5 class="modal-title">Confirmar eliminación</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
      </div>
<%-- Formulario que envía el ID al EliminarUsuarioServlet --%>
      <form method="get" action="<%=request.getContextPath()%>/admin/eliminar-usuario">
        <div class="modal-body">
          <p>¿Estás seguro de que deseas eliminar este usuario?</p>
          <input type="hidden" name="id" id="usuarioEliminarId">
        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
            Cancelar
          </button>
          <button type="submit" class="btn btn-danger">
            Sí, eliminar
          </button>
        </div>
      </form>

    </div>
  </div>
</div>
<%--
    Scripts JavaScript:
    - Filtro dinámico de tabla
    - Captura de ID para eliminación
--%>
<script>
// Filtro en tiempo real sobre la tabla de usuarios
document.getElementById("buscarUsuario").addEventListener("keyup", function () {
    let filtro = this.value.toLowerCase();
    document.querySelectorAll("table tbody tr").forEach(fila => {
        fila.style.display = fila.textContent.toLowerCase().includes(filtro) ? "" : "none";
    });
});
// Captura dinámica del ID del usuario a eliminar
const modalEliminar = document.getElementById('modalEliminar');
modalEliminar.addEventListener('show.bs.modal', function (event) {
    const boton = event.relatedTarget;
    document.getElementById('usuarioEliminarId').value =
        boton.getAttribute('data-id');
});
</script>
<%-- Inclusión del pie de página global --%>
<jsp:include page="/includes/footer.jsp" />