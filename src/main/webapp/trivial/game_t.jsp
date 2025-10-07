<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Country Capital's Game</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background-color: #f2f0eb; /* color suave de fondo */
        }

        .card {
            background-color: #ffffff;
            border-radius: 15px;
        }

        .btn-submit {
            background-color: #6f42c1;
            color: white;
        }

        .btn-submit:hover {
            background-color: #5a3399;
            color: white;
        }

        .score-badge {
            background-color: #fd7e14;
            font-size: 1.2rem;
        }

        .form-check-label {
            font-size: 1rem;
        }

        .option-container {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }
    </style>
</head>

<body>

    <jsp:include page="../shared/menu.jsp" />

    <div class="container mt-5 d-flex justify-content-center">
        <div class="col-md-6">

            <div class="card shadow-lg p-4">
                <h2 class="card-title text-center mb-4 text-primary"><i class="bi bi-brightness-high-fill"></i> Trivial Game 🧠</h2>

                <!-- Pregunta -->
                <div class="mb-4 text-center">
                    <h4 class="text-dark">${question.question}?</h4>
                </div>

                <!-- Opciones -->
                <form method="post" action="TrivialServlet" class="d-flex flex-column gap-3">
                    <div class="option-container">
                        <c:forEach var="option" items="${question.options}">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="selectedAnswer"
                                    id="option${option}" value="${option}" required>
                                <label class="form-check-label text-dark" for="option${option}">${option}</label>
                            </div>
                        </c:forEach>
                    </div>

                    <button type="submit" class="btn btn-submit mt-3 w-100">Submit Answer</button>
                </form>

                <!-- Score -->
                <div class="mt-4 text-center">
                    <span class="fw-bold">Score:</span>
                    <span class="badge score-badge">${score}</span>
                </div>

                <!-- Botones adicionales -->
                <div class="mt-4 d-flex flex-column gap-2">
                    <form method="get" action="ScoreServlet">
                        <button type="submit" class="btn btn-info w-100 text-white"><i class="bi bi-trophy-fill"></i> View High Scores</button>
                    </form>

                    <form method="get" action="../shared/LogoutServlet">
                        <button type="submit" class="btn btn-danger w-100"><i class="bi bi-box-arrow-right"></i> Logout</button>
                    </form>
                </div>

            </div>

        </div>
    </div>

    <jsp:include page="../shared/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>
