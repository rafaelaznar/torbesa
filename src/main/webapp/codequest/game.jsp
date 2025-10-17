<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="es">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>CodeQuest - Juego</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
        </head>

        <body class="bg-light">
            <jsp:include page="../shared/menu.jsp" />
            <div class="container mt-5">
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <div class="card shadow-lg">
                            <div class="card-body">
                                <h2 class="card-title text-center mb-4">¿Cuál es la descripción correcta?</h2>
                                <c:choose>
                                    <c:when test="${not empty technology && not empty options}">
                                        <form method="post" action="GameServlet">
                                            <div class="mb-4 text-center">
                                                <h4>¿Qué describe a <span class="text-primary">${technology}</span>?
                                                </h4>
                                            </div>
                                            <input type="hidden" name="technology" value="${technology}">
                                            <div class="mb-3">
                                                <c:forEach var="option" items="${options}">
                                                    <div class="form-check mb-2">
                                                        <input class="form-check-input" type="radio"
                                                            name="descriptionGuess" id="option${option}"
                                                            value="${option}" required>
                                                        <label class="form-check-label"
                                                            for="option${option}">${option}</label>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                            <button type="submit" class="btn btn-success w-100">Responder</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="alert alert-danger text-center">
                                            <i class="bi bi-exclamation-triangle"></i> No se pudo cargar la
                                            pregunta. Intenta de nuevo.
                                        </div>
                                        <a href="GameServlet" class="btn btn-warning w-100 mb-2">
                                            <i class="bi bi-arrow-repeat"></i> Intentar de nuevo
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                                <div class="mt-4 text-center">
                                    <span class="fw-bold">Puntuación:</span> <span
                                        class="badge bg-primary">${score}</span>
                                </div>
                                <form method="get" action="score" class="mt-3">
                                    <button type="submit" class="btn btn-outline-info w-100">Ver Ranking</button>
                                </form>
                                <form method="get" action="../shared/LogoutServlet" class="mt-2">
                                    <button type="submit" class="btn btn-outline-danger w-100">Cerrar
                                        sesión</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <jsp:include page="../shared/footer.jsp" />
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>