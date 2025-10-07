<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Star Wars's Game</title>
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
                        <h2 class="card-title text-center mb-4">Guess the species of Star Wars characters!</h2>
                        
                        <form method="post" action="GameServlet">
                            <div class="mb-4 text-center">
                                <h4>What is the species of this character?</h4>
                                <p class="lead text-primary fw-bold mt-3">${character.name}</p>
                            </div>

                            <input type="hidden" name="correctSpeciesName" value="${character.species}">

                            <div class="mb-3 d-flex justify-content-center">
                                <div class="w-50 text-start">
                                    <c:forEach var="option" items="${options}">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="characterGuess"
                                                   id="option${option}" value="${option}" required>
                                            <label class="form-check-label"
                                                   for="option${option}">${option}</label>
                                        </div>
                                    </c:forEach>
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