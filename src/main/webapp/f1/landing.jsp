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

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow-lg">
                    <div class="card-body text-center">
                        <h2 class="card-title mb-4">F1 Driver Team Game</h2>
                        <p class="lead">Guess which team each driver races for.</p>
                        <form method="get" action="GameServlet" style="display:inline-block; margin-right:10px;">
                            <button type="submit" class="btn btn-primary btn-lg">Play Now</button>
                        </form>
                        <a href="../f1/ScoreServlet" class="btn btn-warning btn-lg">Top scores</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="../shared/footer.jsp" />

</body>

</html>
