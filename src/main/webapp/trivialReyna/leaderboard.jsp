<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="card shadow-sm border-0 mt-4">
    <div class="card-header bg-warning text-dark">
        <h5 class="card-title mb-0">
            <i class="bi bi-trophy"></i> Tabla de Clasificación
        </h5>
    </div>
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0">
                <thead class="table-dark">
                    <tr>
                        <th scope="col"><i class="bi bi-hash"></i> Posición</th>
                        <th scope="col"><i class="bi bi-person-fill"></i> Usuario</th>
                        <th scope="col" class="text-center"><i class="bi bi-star-fill"></i> Puntuación</th>
                        <th scope="col" class="text-center"><i class="bi bi-controller"></i> Intentos</th>
                        <th scope="col" class="text-center"><i class="bi bi-graph-up"></i> Precisión</th>
                        <th scope="col"><i class="bi bi-calendar-event"></i> Fecha/Hora</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="score" items="${highScores}" varStatus="status">
                        <tr class="${score.userId eq sessionScope.sessionUser.id ? 'table-success' : (status.index < 3 ? (status.index == 0 ? 'table-warning' : (status.index == 1 ? 'table-info' : 'table-light')) : '')}">
                            <td>
                                <c:choose>
                                    <c:when test="${status.index == 0}">
                                        <h5><i class="bi bi-trophy-fill text-warning"></i> 1º</h5>
                                    </c:when>
                                    <c:when test="${status.index == 1}">
                                        <h5><i class="bi bi-award-fill text-secondary"></i> 2º</h5>
                                    </c:when>
                                    <c:when test="${status.index == 2}">
                                        <h5><i class="bi bi-award text-bronze"></i> 3º</h5>
                                    </c:when>
                                    <c:otherwise>
                                        <h5>${status.index + 1}º</h5>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <strong class="h5 ${score.userId eq sessionScope.sessionUser.id ? 'text-success' : ''}">
                                    ${score.username}
                                    <c:if test="${score.userId eq sessionScope.sessionUser.id}">
                                        <i class="bi bi-person-check-fill text-success"></i>
                                    </c:if>
                                </strong>
                            </td>
                            <td class="text-center">
                                <h4>
                                    <span class="badge ${score.userId eq sessionScope.sessionUser.id ? 'bg-success' : 'bg-secondary'}">
                                        ${score.score}
                                    </span>
                                </h4>
                            </td>
                            <td class="text-center">
                                <h4>
                                    <span class="badge ${score.userId eq sessionScope.sessionUser.id ? 'bg-info' : 'bg-secondary'}">
                                        ${score.tries}
                                    </span>
                                </h4>
                            </td>
                            <td class="text-center">
                                <h4>
                                    <span class="badge ${score.userId eq sessionScope.sessionUser.id ? 'bg-primary' : 'bg-secondary'}">
                                        <c:choose>
                                            <c:when test="${score.tries > 0}">
                                                <fmt:formatNumber value="${(score.score / score.tries) * 100}" maxFractionDigits="1" />%
                                            </c:when>
                                            <c:otherwise>0.0%</c:otherwise>
                                        </c:choose>
                                    </span>
                                </h4>
                            </td>
                            <td>
                                <small class="text-muted h5">
                                    <fmt:formatDate value="${score.timestampAsDate}" pattern="dd/MM/yyyy HH:mm" />
                                </small>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty highScores}">
                        <tr>
                            <td colspan="6" class="text-center py-4">
                                <i class="bi bi-emoji-smile display-1 text-muted"></i>
                                <p class="h4 mt-3 text-muted">¡Sé el primero en jugar!</p>
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<style>
.text-bronze {
    color: #CD7F32;
}
</style>
