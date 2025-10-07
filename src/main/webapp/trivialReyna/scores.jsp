<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    if (session.getAttribute("sessionUser") == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Game Results - Trivial Reyna</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">
    <jsp:include page="../shared/menu.jsp" />
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <div class="text-center mb-4">
                    <h1 class="display-4 text-primary">
                        <i class="bi bi-lightbulb-fill"></i> Resultados del Trivial
                    </h1>
                </div>
                <c:if test="${not empty message}">
                    <div class="alert ${message.startsWith('✅') ? 'alert-success' : 'alert-danger'} alert-dismissible fade show" role="alert">
                        ${message}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                <div class="row g-4">
                    <div class="col-md-6">
                        <div class="card shadow-sm border-0 h-100">
                            <div class="card-header bg-info text-white">
                                <h5 class="card-title mb-0">
                                    <i class="bi bi-question-circle-fill"></i> Pregunta y Respuesta
                                </h5>
                            </div>
                            <div class="card-body">
                                <div class="mb-3">
                                    <h5>
                                        <strong class="text-muted">Pregunta:</strong>
                                        <p class="badge bg-primary ms-2">${questionText}</p>
                                    </h5>
                                </div>
                                <div class="mb-3">
                                    <h5>
                                        <strong class="text-muted">Tu respuesta:</strong>
                                        <span class="badge bg-warning text-dark ms-2">${selectedAnswer}</span>
                                    </h5>
                                </div>
                                <div class="mb-0">
                                    <h5>
                                        <strong class="text-muted">Respuesta correcta:</strong>
                                        <span class="badge bg-success ms-2">${correctAnswer}</span>
                                    </h5>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card shadow-sm border-0 h-100">
                            <div class="card-header bg-success text-white">
                                <h5 class="card-title mb-0">
                                    <i class="bi bi-star-fill"></i> Tu Rendimiento
                                </h5>
                            </div>
                            <div class="card-body">
                                <div class="row text-center">
                                    <div class="col-4">
                                        <div class="border-end">
                                            <h3 class="text-success mb-1">
                                                <c:choose>
                                                    <c:when test="${not empty userScore}">
                                                        ${userScore.score}
                                                    </c:when>
                                                    <c:otherwise>0</c:otherwise>
                                                </c:choose>
                                            </h3>
                                            <small class="text-muted">Puntuación</small>
                                        </div>
                                    </div>
                                    <div class="col-4">
                                        <div class="border-end">
                                            <h3 class="text-info mb-1">
                                                <c:choose>
                                                    <c:when test="${not empty userScore}">
                                                        ${userScore.tries}
                                                    </c:when>
                                                    <c:otherwise>0</c:otherwise>
                                                </c:choose>
                                            </h3>
                                            <small class="text-muted">Intentos</small>
                                        </div>
                                    </div>
                                    <div class="col-4">
                                        <div>
                                            <h6 class="text-primary mb-1 h3">
                                                <c:choose>
                                                    <c:when test="${not empty userScore and userScore.tries > 0}">
                                                        <fmt:formatNumber value="${(userScore.score / userScore.tries) * 100}" maxFractionDigits="1" />
                                                    </c:when>
                                                    <c:otherwise>0.0</c:otherwise>
                                                </c:choose>%
                                            </h6>
                                            <small class="text-muted">Precisión</small>
                                        </div>
                                    </div>
                                </div>
                                <hr>
                                <div class="text-center">
                                    <small class="text-muted h5">
                                        <i class="bi bi-calendar-event"></i>
                                        <fmt:formatDate value="${userScore.timestampAsDate}" pattern="MMM dd, yyyy 'at' HH:mm"/>
                                    </small>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="text-center mt-4">
                    <form method="get" action="GameServlet" class="d-inline me-3">
                        <button type="submit" class="btn btn-primary btn-lg">
                            <i class="bi bi-play-fill"></i> Play Again
                        </button>
                    </form>
                    <form method="get" action="../shared/LogoutServlet" class="d-inline">
                        <button type="submit" class="btn btn-outline-danger btn-lg">
                            <i class="bi bi-box-arrow-right"></i> Logout
                        </button>
                    </form>
                </div>
                <jsp:include page="leaderboard.jsp" />
            </div>
        </div>
    </div>
    <jsp:include page="../shared/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
