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
    <title>High Scores - Dog's Game</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
</head>

<body class="bg-light">

    <jsp:include page="../shared/menu.jsp" />

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-10">

                <!-- Header -->
                <div class="text-center mb-4">
                    <h1 class="display-4 text-primary">
                        <i class="bi bi-trophy-fill"></i> Dog Game Leaderboard
                    </h1>
                    <p class="lead text-muted">See who's the top dog breed expert!</p>
                </div>

                <!-- High Scores Table -->
                <jsp:include page="tablaLider.jsp" />

                <!-- Action Buttons -->
                <div class="card shadow-sm border-0 mt-4">
                    <div class="card-body text-center p-4">
                        <h5 class="card-title mb-3">
                            <i class="bi bi-joystick"></i> Ready to play?
                        </h5>
                        <div class="d-flex justify-content-center gap-3 flex-wrap">
                            <form method="get" action="JuegoServlet" class="d-inline">
                                <button type="submit" class="btn btn-primary btn-lg px-4 py-2">
                                    <i class="bi bi-play-fill"></i> Start Playing
                                </button>
                            </form>

                            <form method="get" action="landing.jsp" class="d-inline">
                                <button type="submit" class="btn btn-outline-info btn-lg px-4 py-2">
                                    <i class="bi bi-house-fill"></i> Back to Home
                                </button>
                            </form>

                            <form method="get" action="../shared/LogoutServlet" class="d-inline">
                                <button type="submit" class="btn btn-outline-danger btn-lg px-4 py-2">
                                    <i class="bi bi-box-arrow-right"></i> Logout
                                </button>
                            </form>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <jsp:include page="../shared/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>