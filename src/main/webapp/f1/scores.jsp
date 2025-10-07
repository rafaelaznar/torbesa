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
    <title>F1 Game Results</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<jsp:include page="../shared/menu.jsp" />

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-lg-10">
            <div class="text-center mb-4">
                <h1 class="display-4 text-primary">F1 Game Results</h1>
            </div>

            <c:if test="${not empty message}">
                <div class="alert alert-info">${message}</div>
            </c:if>

            <div class="row g-4">
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <h5>Driver: <span class="badge bg-primary">${driverName}</span></h5>
                            <h5>Your Guess: <span class="badge bg-warning text-dark">${teamGuess}</span></h5>
                            <h5>Correct Team: <span class="badge bg-success">${correctTeam}</span></h5>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <h5>Your Performance</h5>
                            <div>Score: ${userScore.score}</div>
                            <div>Tries: ${userScore.tries}</div>
                        </div>
                    </div>
                </div>
            </div>

            <jsp:include page="leaderboard.jsp" />

            <div class="text-center mt-4">
                <form method="get" action="ScoreServlet" class="d-inline">
                    <button type="submit" class="btn btn-outline-primary btn-lg">View Leaderboard</button>
                </form>
            </div>

        </div>
    </div>
</div>

<jsp:include page="../shared/footer.jsp" />

</body>
</html>
