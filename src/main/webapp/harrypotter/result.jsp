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
    <title>Quiz de Harry Potter - Resultados</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">

    <jsp:include page="../shared/menu.jsp" />

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-10">

                <!-- Header -->
                <div class="text-center mb-4">
                    <h1 class="display-4 text-primary">
                        <i class="bi bi-magic"></i> Harry Potter Quiz - Results
                    </h1>
                    <p class="lead text-muted">Let's see how magical your knowledge is 🪄✨</p>
                </div>

                <!-- Message Alert -->
                <c:if test="${not empty message}">
                    <div class="alert ${houseGuess eq correctHouse ? 'alert-success' : 'alert-warning'} alert-dismissible fade show" role="alert">
                        <i class="bi bi-${houseGuess eq correctHouse ? 'check-circle-fill' : 'exclamation-triangle-fill'}"></i> ${message}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <div class="row g-4">
                    <!-- Character and Answer Card -->
                    <div class="col-md-6">
                        <div class="card shadow-sm border-0 h-100">
                            <div class="card-header bg-primary text-white">
                                <h5 class="card-title mb-0">
                                    <i class="bi bi-person-bounding-box"></i> Tu Respuesta
                                </h5>
                            </div>
                            <div class="card-body">
                                <div class="mb-3">
                                    <h5>
                                        <strong class="text-muted">Personaje:</strong>
                                        <span class="badge bg-primary ms-2">${characterName}</span>
                                    </h5>
                                </div>
                                <div class="mb-3">
                                    <h5>
                                        <strong class="text-muted">Tu Respuesta:</strong>
                                        <span class="badge bg-warning text-dark ms-2">${houseGuess}</span>
                                    </h5>
                                </div>
                                <div class="mb-0">
                                    <h5>
                                        <strong class="text-muted">Casa Correcta:</strong>
                                        <span class="badge bg-success ms-2">${correctHouse}</span>
                                    </h5>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Performance Card -->
                    <div class="col-md-6">
                        <div class="card shadow-sm border-0 h-100">
                            <div class="card-header bg-success text-white">
                                <h5 class="card-title mb-0">
                                    <i class="bi bi-star-fill"></i> Tu Rendimiento
                                </h5>
                            </div>
                            <div class="card-body text-center">
                                <c:choose>
                                    <c:when test="${houseGuess eq correctHouse}">
                                        <h3 class="text-success mb-3">
                                            <i class="bi bi-check-circle-fill"></i> ¡Correcto!
                                        </h3>
                                        <p class="lead">¡Eres un verdadero mago! 🧙‍♂️</p>
                                        <div class="mt-3">
                                            <span class="badge bg-success fs-6">¡+1 Punto ganado!</span>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <h3 class="text-warning mb-3">
                                            <i class="bi bi-x-circle-fill"></i> Incorrecto
                                        </h3>
                                        <p class="lead">¡Mejor suerte la próxima vez! ¡Sigue practicando tu magia! 🪄</p>
                                        <div class="mt-3">
                                            <span class="badge bg-warning text-dark fs-6">¡Inténtalo de nuevo!</span>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Action Buttons -->
                <div class="text-center mt-4">
                    <a href="GameServlet" class="btn btn-primary btn-lg me-3">
                        <i class="bi bi-play-fill"></i> Jugar de Nuevo
                    </a>

                    <a href="ScoreServlet" class="btn btn-info btn-lg me-3">
                        <i class="bi bi-trophy"></i> Ver Clasificación
                    </a>

                    <a href="landing.jsp" class="btn btn-outline-secondary btn-lg me-3">
                        <i class="bi bi-house-door"></i> Volver al Menú
                    </a>

                    <a href="../shared/welcome.jsp" class="btn btn-outline-danger btn-lg">
                        <i class="bi bi-grid"></i> Menú Principal
                    </a>
                </div>

            </div>
        </div>
    </div>

    <jsp:include page="../shared/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

