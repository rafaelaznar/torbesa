<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <c:if test="${sessionScope.sessionUser == null}">
                <c:redirect url="../index.jsp" />
            </c:if>
            <!DOCTYPE html>
            <html lang="es">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Resultados - CodeQuest</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css"
                    rel="stylesheet">
            </head>

            <body class="bg-light">
                <jsp:include page="../shared/menu.jsp" />
                <div class="container py-5">
                    <div class="row justify-content-center">
                        <div class="col-lg-10">
                            <!-- Cabecera -->
                            <div class="text-center mb-4">
                                <h1 class="display-4 text-primary">
                                    <i class="bi bi-trophy-fill"></i> Resultados del juego
                                </h1>
                            </div>
                            <!-- Mensaje de alerta -->
                            <c:if test="${not empty message}">
                                <div class="alert alert-info alert-dismissible fade show" role="alert">
                                    <i class="bi bi-info-circle-fill"></i> ${message}
                                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                </div>
                            </c:if>
                            <div class="row g-4">
                                <!-- Tarjeta de tu respuesta -->
                                <div class="col-md-6">
                                    <div class="card shadow-sm border-0 h-100">
                                        <div class="card-header bg-info text-white">
                                            <h5 class="card-title mb-0">
                                                <i class="bi bi-question-circle-fill"></i> Tu respuesta
                                            </h5>
                                        </div>
                                        <div class="card-body">
                                            <div class="mb-3">
                                                <h5>
                                                    <strong class="text-muted">Tecnología:</strong>
                                                    <span class="badge bg-primary ms-2">${technology}</span>
                                                </h5>
                                            </div>
                                            <div class="mb-3">
                                                <h5>
                                                    <strong class="text-muted">Tu descripción:</strong>
                                                    <div class="bg-warning text-dark rounded p-2 ms-2 text-break d-inline-block"
                                                        style="max-width: 100%; white-space: pre-line;">
                                                        ${descriptionGuess}
                                                    </div>
                                                </h5>
                                            </div>
                                            <div class="mb-0">
                                                <h5>
                                                    <strong class="text-muted">Descripción correcta:</strong>
                                                    <div class="bg-success text-white rounded p-2 ms-2 text-break d-inline-block"
                                                        style="max-width: 100%; white-space: pre-line;">
                                                        ${correctDescription}
                                                    </div>
                                                </h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- Tarjeta de tu puntuación -->
                                <div class="col-md-6">
                                    <div class="card shadow-sm border-0 h-100">
                                        <div class="card-header bg-success text-white">
                                            <h5 class="card-title mb-0">
                                                <i class="bi bi-star-fill"></i> Tu rendimiento
                                            </h5>
                                        </div>
                                        <div class="card-body">
                                            <c:choose>
                                                <c:when test="${not empty userScore}">
                                                    <div class="row text-center">
                                                        <div class="col-4">
                                                            <div class="border-end">
                                                                <h3 class="text-success mb-1">${userScore.score}</h3>
                                                                <small class="text-muted">Puntuación</small>
                                                            </div>
                                                        </div>
                                                        <div class="col-4">
                                                            <div class="border-end">
                                                                <h3 class="text-info mb-1">${userScore.tries}</h3>
                                                                <small class="text-muted">Intentos</small>
                                                            </div>
                                                        </div>
                                                        <div class="col-4">
                                                            <div>
                                                                <h6 class="text-primary mb-1 h3">
                                                                    <c:choose>
                                                                        <c:when test="${userScore.tries > 0}">
                                                                            <fmt:formatNumber
                                                                                value="${userScore.score / userScore.tries * 100}"
                                                                                maxFractionDigits="1" />%
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            0%
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </h6>
                                                                <small class="text-muted">Precisión</small>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <hr>
                                                    <div class="text-center">
                                                        <small class="text-muted h5">
                                                            <i class="bi bi-calendar-event"></i>
                                                            <c:choose>
                                                                <c:when test="${not empty userScore.timestampAsDate}">
                                                                    <fmt:formatDate value="${userScore.timestampAsDate}"
                                                                        pattern="MMM dd, yyyy 'a las' HH:mm" />
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="text-muted">Sin fecha</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </small>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="text-center text-muted py-4">
                                                        <i class="bi bi-info-circle display-4"></i>
                                                        <h5 class="mt-2">No hay puntuaciones disponibles</h5>
                                                        <p class="mb-0">¡Sé el primero en jugar!</p>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Botones de acción -->
                            <div class="text-center mt-4">
                                <form method="get" action="GameServlet" class="d-inline me-3">
                                    <button type="submit" class="btn btn-primary btn-lg">
                                        <i class="bi bi-play-fill"></i> Jugar de nuevo
                                    </button>
                                </form>
                                <form method="get" action="../shared/LogoutServlet" class="d-inline">
                                    <button type="submit" class="btn btn-outline-danger btn-lg">
                                        <i class="bi bi-box-arrow-right"></i> Salir
                                    </button>
                                </form>
                            </div>
                            <!-- Tabla de puntuaciones altas -->
                            <jsp:include page="leaderboard.jsp" />
                        </div>
                    </div>
                </div>
                <jsp:include page="../shared/footer.jsp" />
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            </body>

            </html>