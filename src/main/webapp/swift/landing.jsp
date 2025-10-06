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
        <title>Country Borders Game</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    </head>

    <body>
        <jsp:include page="../shared/menu.jsp" />

        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-8 text-center">
                    <h1 class="display-4">Welcome to the Country Capital's Game!</h1>
                    <p class="lead">Test your geography skills by guessing country capitals!</p>
                    <a href="../capitals/GameServlet" class="btn btn-success btn-lg m-4">Start Playing</a>
                    <a href="../capitals/ScoreServlet" class="btn btn-warning btn-lg m-4">Top scores</a>
                </div>
            </div>
            <div class="row mt-5">
                <div class="col-md-8 offset-md-2">
                    <div class="card shadow">
                        <div class="card-body">
                            <h5 class="card-title">How to Play</h5>
                            <ul class="list-group list-group-flush text-start">
                                <li class="list-group-item">Log in with your username and password.</li>
                                <li class="list-group-item">Guess the country by its borders.</li>
                                <li class="list-group-item">Earn points for correct answers.</li>
                                <li class="list-group-item">View high scores and challenge your friends!</li>
                                <li class="list-group-item">Also this code is MIT licensed.</li>
                                <li class="list-group-item">Use this code to learn java, servlets, jsp, jstl, sessions
                                    and more!</li>
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