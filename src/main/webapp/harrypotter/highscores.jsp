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
    <title>Harry Potter - High Scores</title>
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
                    <i class="bi bi-magic"></i> Harry Potter Quiz - Top Scores
                </h1>
                <p class="lead text-muted">The most magical wizards and witches! 🪄✨</p>
            </div>

            <!-- Message Alert -->
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="bi bi-exclamation-triangle-fill"></i> ${errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <!-- Harry Potter Leaderboard -->
            <jsp:include page="leaderboard.jsp" />

            <!-- Action Buttons -->
            <div class="text-center mt-4">
                <a href="GameServlet" class="btn btn-primary btn-lg me-3">
                    <i class="bi bi-play-fill"></i> Play Quiz
                </a>
                <a href="landing.jsp" class="btn btn-outline-secondary btn-lg me-3">
                    <i class="bi bi-house-door"></i> Back to Landing
                </a>
                <a href="../shared/welcome.jsp" class="btn btn-outline-info btn-lg">
                    <i class="bi bi-grid"></i> Main Menu
                </a>
            </div>

        </div>
    </div>
</div>

<jsp:include page="../shared/footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
