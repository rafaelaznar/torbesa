<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <!-- Izquierda: Nombre de la aplicación -->
        <a class="navbar-brand" href="landing.jsp">Country Capital's Game</a>
        <!-- Esto empuja el siguiente bloque a la derecha -->
        <c:if test="${not empty sessionScope.sessionUser}">
            <ul class="navbar-nav ms-auto flex-row">
                <li class="nav-item">
                    <span class="navbar-text">
                        <i class="bi bi-person-circle"></i>
                        <c:out value="${sessionScope.sessionUser.username}" />
                    </span>
                </li>
            </ul>
        </c:if>
    </div>
</nav>