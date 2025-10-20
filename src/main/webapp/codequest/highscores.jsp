<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <% if (session.getAttribute("sessionUser")==null) { response.sendRedirect(request.getContextPath()
                + "/index.jsp" ); return; } %>
                <!DOCTYPE html>
                <html lang="es">

                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Ranking CodeQuest</title>
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
                        rel="stylesheet">
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css"
                        rel="stylesheet">
                </head>

                <body class="bg-light">
                    <jsp:include page="../shared/menu.jsp" />
                    <div class="container py-5">
                        <div class="row justify-content-center">
                            <div class="col-lg-10">
                                <h1 class="text-center mb-4">
                                    <i class="bi bi-trophy-fill text-warning"></i> Ranking CodeQuest
                                </h1>
                                <!-- Tabla de Ranking -->
                                <jsp:include page="leaderboard.jsp" />
                                <!-- Botones de acción -->
                                <div class="text-center mt-4">
                                    <form method="get" action="GameServlet" class="d-inline me-3">
                                        <button type="submit" class="btn btn-primary btn-lg">
                                            <i class="bi bi-play-fill"></i> Jugar
                                        </button>
                                    </form>
                                    <c:if test="${not empty user}">
                                        <form method="get" action="LogoutServlet" class="d-inline">
                                            <button type="submit" class="btn btn-outline-danger btn-lg">
                                                <i class="bi bi-box-arrow-right"></i> Cerrar sesión
                                            </button>
                                        </form>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <jsp:include page="../shared/footer.jsp" />
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
                </body>

                </html>