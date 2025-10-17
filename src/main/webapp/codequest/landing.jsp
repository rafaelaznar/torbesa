<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <% if (session.getAttribute("sessionUser")==null) { response.sendRedirect(request.getContextPath() + "/index.jsp" );
        return; } %>
        <!DOCTYPE html>
        <html lang="es">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>CodeQuest - Desafío de Programación</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
        </head>

        <body>
            <jsp:include page="../shared/menu.jsp" />
            <div class="container mt-5">
                <div class="row justify-content-center">
                    <div class="col-md-8 text-center">
                        <h1 class="display-4">¡Bienvenido a CodeQuest!</h1>
                        <p class="lead">Pon a prueba tus conocimientos de programación adivinando tecnologías,
                            lenguajes y frameworks.</p>
                        <a href="../codequest/GameServlet" class="btn btn-primary btn-lg m-4">Jugar</a>
                        <a href="../codequest/score" class="btn btn-warning btn-lg m-4">Ranking</a>
                    </div>
                </div>
                <div class="row mt-5">
                    <div class="col-md-8 offset-md-2">
                        <div class="card shadow">
                            <div class="card-body">
                                <h5 class="card-title">¿Cómo se juega?</h5>
                                <ul class="list-group list-group-flush text-start">
                                    <li class="list-group-item">Inicia sesión con tu usuario y contraseña.</li>
                                    <li class="list-group-item">Acierta la tecnología a partir de su descripción o
                                        pista.</li>
                                    <li class="list-group-item">Gana puntos por cada respuesta correcta.</li>
                                    <li class="list-group-item">Consulta el ranking y reta a tus amigos.</li>
                                    <li class="list-group-item">Aprende sobre tecnologías reales del mundo laboral.
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <jsp:include page="../shared/footer.jsp" />
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>