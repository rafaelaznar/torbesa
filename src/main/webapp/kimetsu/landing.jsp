<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Kimetsu Game</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    </head>

    <body>
        <jsp:include page="../shared/menu.jsp" />

        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-8 text-center">
                    <h1 class="display-4">Bienvenido al juego de Kimetsu!</h1>
                    <p class="lead">Testea tu sabiduria adivinando cada pregunta</p>
                    <a href="../kimetsu/GameServlet" class="btn btn-success btn-lg m-4">Empezar a jugar</a>
                    <a href="../kimetsu/ScoreServlet" class="btn btn-warning btn-lg m-4">Top scores</a>
                </div>
            </div>
            <div class="row mt-5">
                <div class="col-md-8 offset-md-2">
                    <div class="card shadow">
                        <div class="card-body">
                            <h5 class="card-title">¿Como se juega?</h5>
                            <ul class="list-group list-group-flush text-start">
                                <li class="list-group-item">Logueate con tu usuario y contraseña.</li>
                                <li class="list-group-item">Adivina la respuesta correcta.</li>
                                <li class="list-group-item">Gana puntos por cada respuesata correcta.</li>
                                <li class="list-group-item">Visualiza el Top y reta a tus amigos.</li>
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