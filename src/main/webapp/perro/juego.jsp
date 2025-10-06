<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Country Capital's Game</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
        </head>

        <body class="bg-light">

            <jsp:include page="../shared/menu.jsp" />

            <div class="container mt-5">
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <div class="card shadow-lg">
                            <div class="card-body">
                                <h2 class="card-title text-center mb-4">¿Qué raza es este perro?</h2>
                                <div class="mb-4 text-center">
                                    <img src="${imagenUrl}" alt="Imagen de perro" class="img-fluid rounded" style="max-height: 300px;">
                                </div>
                                <form method="post" action="JuegoServlet">
                                    <input type="hidden" name="razaCorrecta" value="${razaCorrecta}">
                                    <div class="mb-3 d-flex justify-content-center">
                                        <div class="w-50 text-start">
                                            <c:forEach var="option" items="${opciones}">
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="respuesta"
                                                        id="option${option}" value="${option}" required>
                                                    <label class="form-check-label"
                                                        for="option${option}">${option}</label>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-success w-100">Enviar respuesta</button>
                                </form>
                                <div class="mt-4 text-center">
                                    <span class="fw-bold">Puntuación:</span> <span class="badge bg-primary">${puntuacion}</span>
                                </div>
                                <c:if test="${not empty resultado}">
                                    <div class="alert ${resultado eq '¡Correcto!' ? 'alert-success' : 'alert-danger'} mt-3 text-center">${resultado}</div>
                                </c:if>
                                <form method="get" action="PuntuacionServlet" class="mt-3">
                                    <button type="submit" class="btn btn-outline-info w-100">View High Scores</button>
                                </form>
                                <form method="get" action="../shared/LogoutServlet" class="mt-2">
                                    <button type="submit" class="btn btn-outline-danger w-100">Logout</button>
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