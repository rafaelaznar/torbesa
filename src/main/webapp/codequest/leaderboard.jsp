<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    // Recuperar datos del servlet
    net.ausiasmarch.codequest.model.ScoreDto userScore = (net.ausiasmarch.codequest.model.ScoreDto) request.getAttribute("userScore");
    java.util.List<net.ausiasmarch.codequest.model.ScoreDto> highScores = (java.util.List<net.ausiasmarch.codequest.model.ScoreDto>) request.getAttribute("highScores");
    java.util.List<net.ausiasmarch.codequest.model.ScoreDto> allScores = (java.util.List<net.ausiasmarch.codequest.model.ScoreDto>) request.getAttribute("allScores");
    net.ausiasmarch.shared.model.UserBean sessionUser = (net.ausiasmarch.shared.model.UserBean) session.getAttribute("sessionUser");
%>

<!-- Tu Puntuación Personal -->
<c:if test="${not empty userScore}">
    <div class="card shadow-sm border-primary mb-4">
        <div class="card-header bg-primary text-white">
            <h5 class="card-title mb-0">
                <i class="bi bi-person-badge"></i> Tu Estadística
            </h5>
        </div>
        <div class="card-body">
            <div class="row text-center">
                <div class="col-md-3">
                    <h3 class="text-primary">${userScore.score}</h3>
                    <small class="text-muted">Puntuación Total</small>
                </div>
                <div class="col-md-3">
                    <h3 class="text-info">${userScore.tries}</h3>
                    <small class="text-muted">Partidas Jugadas</small>
                </div>
                <div class="col-md-3">
                    <h3 class="text-success">
                        <fmt:formatNumber value="${userScore.tries > 0 ? (userScore.score / userScore.tries * 100) : 0}" 
                                        maxFractionDigits="1" />%
                    </h3>
                    <small class="text-muted">Precisión</small>
                </div>
                <div class="col-md-3">
                    <h3 class="text-warning">
                        <c:set var="userRank" value="N/A" />
                        <c:if test="${not empty allScores}">
                            <c:forEach items="${allScores}" var="score" varStatus="status">
                                <c:if test="${score.userId == userScore.userId}">
                                    <c:set var="userRank" value="${status.index + 1}" />
                                </c:if>
                            </c:forEach>
                        </c:if>
                        #${userRank}
                    </h3>
                    <small class="text-muted">Ranking Global</small>
                </div>
            </div>
        </div>
    </div>
</c:if>

<!-- Leaderboard Principal -->
<div class="card shadow-sm border-0 mt-4">
    <div class="card-header bg-warning text-dark">
        <h5 class="card-title mb-0">
            <i class="bi bi-trophy"></i> CodeQuest Leaderboard
        </h5>
    </div>
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0">
                <thead class="table-dark">
                    <tr>
                        <th scope="col"><i class="bi bi-hash"></i> Ranking</th>
                        <th scope="col"><i class="bi bi-person-fill"></i> Usuario</th>
                        <th scope="col" class="text-center"><i class="bi bi-star-fill"></i> Puntuación</th>
                        <th scope="col" class="text-center"><i class="bi bi-target"></i> Partidas</th>
                        <th scope="col" class="text-center"><i class="bi bi-percent"></i> Precisión</th>
                        <th scope="col"><i class="bi bi-calendar-event"></i> Fecha/Hora</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty highScores}">
                            <c:forEach var="score" items="${highScores}" varStatus="status">
                                <c:set var="isCurrentUser" value="${not empty userScore && score.userId == userScore.userId}" />
                                <tr class="${isCurrentUser ? 'table-success' : ''} 
                                           ${status.index < 3 && !isCurrentUser ? (status.index == 0 ? 'table-warning' : (status.index == 1 ? 'table-info' : 'table-light')) : ''}">
                                    <td>
                                        <c:choose>
                                            <c:when test="${status.index == 0}">
                                                <h5 class="mb-0"><i class="bi bi-trophy-fill text-warning"></i> #1</h5>
                                            </c:when>
                                            <c:when test="${status.index == 1}">
                                                <h5 class="mb-0"><i class="bi bi-award-fill text-secondary"></i> #2</h5>
                                            </c:when>
                                            <c:when test="${status.index == 2}">
                                                <h5 class="mb-0"><i class="bi bi-award text-danger"></i> #3</h5>
                                            </c:when>
                                            <c:otherwise>
                                                <h5 class="mb-0">#${status.index + 1}</h5>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:if test="${isCurrentUser}">
                                            <small class="text-success"><i class="bi bi-person-check"></i> Tú</small>
                                        </c:if>
                                    </td>
                                    <td>
                                        <strong class="h5">${score.login}</strong>
                                        <c:if test="${isCurrentUser}">
                                            <br><small class="text-primary">¡Eres tú!</small>
                                        </c:if>
                                    </td>
                                    <td class="text-center">
                                        <h4 class="mb-0"><span class="badge bg-success">${score.score}</span></h4>
                                    </td>
                                    <td class="text-center">
                                        <h4 class="mb-0"><span class="badge bg-info">${score.tries}</span></h4>
                                    </td>
                                    <td class="text-center">
                                        <h4 class="mb-0">
                                            <span class="badge bg-primary">
                                                <fmt:formatNumber value="${score.tries > 0 ? (score.score / score.tries * 100) : 0}" 
                                                                maxFractionDigits="1" />%
                                            </span>
                                        </h4>
                                    </td>
                                    <td>
                                        <small class="text-muted">
                                            <fmt:parseDate value="${score.timestamp}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDate" type="both" />
                                            <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yy HH:mm" />
                                        </small>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="6" class="text-center py-4">
                                    <div class="text-muted">
                                        <i class="bi bi-info-circle display-4"></i>
                                        <h5 class="mt-2">No hay puntuaciones registradas</h5>
                                        <p class="mb-0">¡Sé el primero en jugar!</p>
                                    </div>
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</div>