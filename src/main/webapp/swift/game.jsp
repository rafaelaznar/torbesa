<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="en">
 
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Taylor's Songs Game</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
        </head>

        <body class="bg-light">

            <jsp:include page="../shared/menu.jsp" />

            <div class="container mt-5">
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <div class="card shadow-lg">
                            <div class="card-body">
                                <h2 class="card-title text-center mb-4">Guess the Album!</h2>
                                <form method="post" action="GameServlet">
                                    <div class="mb-4 text-center">
                                        <h4>To which album does <span class="text-primary">${title}</span> belongs to?</h4>
                                    </div>
                                    <!-- send the songs name as a hidden field -->
                                    <input type="hidden" name="song" value="${title}">
                                    <div class="mb-3">
                                        <div class="row">
                                            <div class="col-md-7 d-flex justify-content-center">
                                                <div class="w-100 text-start" style="max-width:420px;">
                                                    <c:forEach var="option" items="${options}" varStatus="status">
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="radio" name="albumGuess"
                                                                id="option${status.index}" value="${option}" required>
                                                            <label class="form-check-label"
                                                                for="option${status.index}">${option}</label>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                            <div class="col-md-5">
                                                <div class="card border-secondary h-100">
                                                    <div class="card-body">
                                                        <h5 class="card-title">Lista</h5>
                                                        <ul id="rightList" class="list-group list-group-flush">
                                                            <!-- lista vacía -->
                                                             <li> 1 -> 1989</li>
                                                             <li> 2 -> Taylor Swift</li>
                                                             <li> 3 -> Fearless</li>
                                                             <li> 4 -> Speak Now</li>
                                                             <li> 5 -> Red</li>
                                                             <li> 6 -> Reputation</li>
                                                             <li> 7 -> Lover</li>
                                                             <li> 8 -> Folklore</li>
                                                             <li> 9 -> Evermore</li>
                                                             <li> 10 -> Midnights</li>
                                                             <li> 11 -> The Tortured Poets Department</li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-success w-100">Submit Answer</button>
                                </form>
                                <div class="mt-4 text-center">
                                    <span class="fw-bold">Score:</span> <span class="badge bg-primary">${score}</span>
                                </div>
                                <form method="get" action="ScoreServlet" class="mt-3">
                                    <button type="submit" class="btn btn-outline-info w-100">View High Scores</button>
                                </form>
                                <form method="get" action="../shared/LogoutServlet" class="mt-2">
                                    <button type="submit" class="btn btn-outline-danger w-100">Logout</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <jsp:include page="../shared/footer.jsp" />
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>