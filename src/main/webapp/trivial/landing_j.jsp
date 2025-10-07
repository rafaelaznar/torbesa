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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trivial Game</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background-color: #f2f0eb;
        }

        .btn-play {
            background: linear-gradient(90deg, #28a745, #218838);
            color: white;
        }

        .btn-play:hover {
            background: linear-gradient(90deg, #218838, #1c6d2e);
            color: white;
        }

        .btn-scores {
            background: linear-gradient(90deg, #ffc107, #e0a800);
            color: white;
        }

        .btn-scores:hover {
            background: linear-gradient(90deg, #e0a800, #c69500);
            color: white;
        }

        .card {
            border-radius: 15px;
        }

        .list-group-item {
            background-color: #fff3cd;
            border: none;
        }

        h1.display-4 {
            color: #343a40;
        }

        p.lead {
            color: #495057;
        }
    </style>
</head>

<body>
    <jsp:include page="../shared/menu.jsp" />

    <div class="container mt-5 d-flex flex-column align-items-center">

        <!-- Título y descripción -->
        <div class="text-center mb-5">
            <h1 class="display-4">Welcome to the Trivial Game! 🧠</h1>
            <p class="lead">Test your brain skills by guessing the correct answers!</p>
        </div>

        <!-- Botones principales -->
        <div class="d-flex flex-column gap-3 mb-5 w-50">
            <a href="../trivial/TrivialServlet" class="btn btn-play btn-lg">Start Playing <i class="bi bi-play-fill"></i></a>
            <a href="../trivial/ScoreServlet" class="btn btn-scores btn-lg">Top Scores <i class="bi bi-trophy-fill"></i></a>
        </div>

        <!-- Cómo jugar -->
        <div class="col-md-8">
            <div class="card shadow p-4">
                <h5 class="card-title text-center mb-3">How to Play</h5>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">Log in with your username and password.</li>
                    <li class="list-group-item">Guess the answer to each question.</li>
                    <li class="list-group-item">Earn points for correct answers.</li>
                    <li class="list-group-item">View high scores and challenge your friends!</li>
                    <li class="list-group-item">This project is MIT licensed.</li>
                    <li class="list-group-item">Use it to learn Java, Servlets, JSP, JSTL, sessions, and more!</li>
                </ul>
            </div>
        </div>

    </div>

    <jsp:include page="../shared/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
