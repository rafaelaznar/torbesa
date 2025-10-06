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
    <title>Harry Potter Quiz 🪄</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
</head>

<body>
    <jsp:include page="../shared/menu.jsp" />

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8 text-center">
                <h1 class="display-4 mb-3">
                    <i class="bi bi-magic text-primary"></i> Welcome to the Harry Potter Quiz!
                </h1>
                <p class="lead mb-4">Discover your magical knowledge by guessing the Hogwarts House of each character 🏰✨</p>
                <a href="../harrypotter/GameServlet" class="btn btn-success btn-lg m-3">
                    <i class="bi bi-play-fill"></i> Start Quiz
                </a>
                <a href="../harrypotter/ScoreServlet" class="btn btn-warning btn-lg m-3">
                    <i class="bi bi-trophy"></i> Top Scores
                </a>
            </div>
        </div>

        <div class="row mt-5">
            <div class="col-md-8 offset-md-2">
                <div class="card shadow">
                    <div class="card-body">
                        <h5 class="card-title">How to Play</h5>
                        <ul class="list-group list-group-flush text-start">
                            <li class="list-group-item">🪄 Log in with your username and password.</li>
                            <li class="list-group-item">✨ You'll see the name of a random character.</li>
                            <li class="list-group-item">🏠 Choose which Hogwarts house they belong to (Gryffindor, Hufflepuff, Ravenclaw or Slytherin).</li>
                            <li class="list-group-item">✅ Earn points for each correct answer. Try to get a perfect score!</li>
                            <li class="list-group-item">🏆 View the top scores and challenge your friends!</li>
                            <li class="list-group-item">⚡ This game is built with JSP, Servlets and Sessions — a fun way to learn web dev magic!</li>
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
