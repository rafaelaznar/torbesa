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
    <title>Quiz de Harry Potter 🪄</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
</head>

<body>
    <jsp:include page="../shared/menu.jsp" />

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8 text-center">
                <h1 class="display-4 mb-3">
                    <i class="bi bi-magic text-primary"></i> ¡Bienvenido al Quiz de Harry Potter!
                </h1>
                <p class="lead mb-4">Descubre tu conocimiento mágico adivinando la Casa de Hogwarts de cada personaje 🏰✨</p>
                <a href="GameServlet" class="btn btn-success btn-lg m-3">
                    <i class="bi bi-play-fill"></i> Comenzar Quiz
                </a>
                <a href="ScoreServlet" class="btn btn-warning btn-lg m-3">
                    <i class="bi bi-trophy"></i> Mejores Puntuaciones
                </a>
            </div>
        </div>

        <div class="row mt-5">
            <div class="col-md-8 offset-md-2">
                <div class="card shadow">
                    <div class="card-body">
                        <h5 class="card-title">Cómo Jugar</h5>
                        <ul class="list-group list-group-flush text-start">
                            <li class="list-group-item">🪄 Inicia sesión con tu nombre de usuario y contraseña.</li>
                            <li class="list-group-item">✨ Verás el nombre de un personaje aleatorio.</li>
                            <li class="list-group-item">🏠 Elige a qué casa de Hogwarts pertenece (Gryffindor, Hufflepuff, Ravenclaw o Slytherin).</li>
                            <li class="list-group-item">✅ Gana puntos por cada respuesta correcta. ¡Intenta conseguir una puntuación perfecta!</li>
                            <li class="list-group-item">🏆 ¡Ve las mejores puntuaciones y desafía a tus amigos!</li>
                            <li class="list-group-item">⚡ Este juego está construido con JSP, Servlets y Sessions — ¡una forma divertida de aprender magia de desarrollo web!</li>
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
