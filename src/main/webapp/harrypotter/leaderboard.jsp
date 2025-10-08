<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="card shadow-sm border-0 mt-4">
    <div class="card-header bg-primary text-white">
        <h5 class="card-title mb-0">
            <i class="bi bi-magic"></i> Tabla de Clasificación del Quiz de Harry Potter 🏆
        </h5>
    </div>
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0">
                <thead class="table-dark">
                    <tr>
                        <th scope="col"><i class="bi bi-hash"></i> Posición</th>
                        <th scope="col"><i class="bi bi-person-fill"></i> Mago</th>
                        <th scope="col" class="text-center"><i class="bi bi-star-fill"></i> Puntuación</th>
                        <th scope="col" class="text-center"><i class="bi bi-target"></i> Intentos</th>
                        <th scope="col" class="text-center"><i class="bi bi-percent"></i> Precisión</th>
                        <th scope="col"><i class="bi bi-calendar-event"></i> Fecha/Hora</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty highScores}">
                            <tr>
                                <td colspan="6" class="text-center py-4">
                                    <i class="bi bi-hourglass-split"></i>
                                    <p class="mb-0">¡Aún no hay puntuaciones mágicas! ¡Sé el primer mago en jugar! 🪄</p>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="score" items="${highScores}" varStatus="status">
                                <tr class="${status.index < 3 ? (status.index == 0 ? 'table-warning' : (status.index == 1 ? 'table-info' : 'table-light')) : ''}">
                                    <td>
                                        <c:choose>
                                            <c:when test="${status.index == 0}">
                                                <h5><i class="bi bi-trophy-fill text-warning"></i> #1</h5>
                                            </c:when>
                                            <c:when test="${status.index == 1}">
                                                <h5><i class="bi bi-award-fill text-info"></i> #2</h5>
                                            </c:when>
                                            <c:when test="${status.index == 2}">
                                                <h5><i class="bi bi-award text-secondary"></i> #3</h5>
                                            </c:when>
                                            <c:otherwise>
                                                <h5>#${status.index + 1}</h5>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <strong class="h5">
                                            <i class="bi bi-person-badge"></i> 
                                            <c:choose>
                                                <c:when test="${not empty score.username}">
                                                    ${score.username}
                                                </c:when>
                                                <c:otherwise>
                                                    Mago Desconocido
                                                </c:otherwise>
                                            </c:choose>
                                        </strong>
                                    </td>
                                    <td class="text-center">
                                        <h4>
                                            <span class="badge bg-success">
                                                <i class="bi bi-lightning-fill"></i> 
                                                <c:choose>
                                                    <c:when test="${score.score != null}">
                                                        ${score.score}
                                                    </c:when>
                                                    <c:otherwise>
                                                        0
                                                    </c:otherwise>
                                                </c:choose>
                                            </span>
                                        </h4>
                                    </td>
                                    <td class="text-center">
                                        <h4>
                                            <span class="badge bg-info">
                                                <i class="bi bi-bullseye"></i> 
                                                <c:choose>
                                                    <c:when test="${score.tries != null}">
                                                        ${score.tries}
                                                    </c:when>
                                                    <c:otherwise>
                                                        0
                                                    </c:otherwise>
                                                </c:choose>
                                            </span>
                                        </h4>
                                    </td>
                                    <td class="text-center">
                                        <h4>
                                            <span class="badge bg-primary">
                                                <c:choose>
                                                    <c:when test="${score.tries != null && score.tries > 0 && score.score != null}">
                                                        ${Math.round(score.score * 100.0 / score.tries)}%
                                                    </c:when>
                                                    <c:otherwise>
                                                        0%
                                                    </c:otherwise>
                                                </c:choose>
                                            </span>
                                        </h4>
                                    </td>
                                    <td>
                                        <small class="text-muted h5">
                                            <i class="bi bi-clock"></i>
                                            ${score.timestamp}
                                        </small>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer bg-light text-center">
        <small class="text-muted">
            <i class="bi bi-magic"></i> ¡Que la magia te acompañe! ¡Lanza tus hechizos sabiamente! ⚡
        </small>
    </div>
</div>
