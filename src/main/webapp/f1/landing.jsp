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
    <title>F1 - Landing</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

    <jsp:include page="../shared/menu.jsp" />

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8 text-center">
                <h1 class="display-4">Welcome to the F1 Driver Team Game!</h1>
                <p class="lead">Test your F1 knowledge by guessing each driver's team!</p>
                <a href="../f1/GameServlet" class="btn btn-success btn-lg m-4">Start Playing</a>
                <a href="../f1/ScoreServlet" class="btn btn-warning btn-lg m-4">Top scores</a>
            </div>
        </div>
        <div class="row mt-5">
            <div class="col-md-8 offset-md-2">
                <div class="card shadow">
                    <div class="card-body">
                        <h5 class="card-title">How to Play</h5>
                        <ul class="list-group list-group-flush text-start">
                            <li class="list-group-item">Log in with your username and password.</li>
                            <li class="list-group-item">Guess the team the driver races for.</li>
                            <li class="list-group-item">Earn points for correct answers.</li>
                            <li class="list-group-item">View high scores and challenge your friends!</li>
                            <li class="list-group-item">Also this code is MIT licensed.</li>
                            <li class="list-group-item">Use this code to learn java, servlets, jsp, jstl, sessions and more!</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="../shared/footer.jsp" />

</body>

</html>
